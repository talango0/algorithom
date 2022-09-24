package leetcode.math;

import java.util.*;

/**
 * @author mayanwei
 * @date 2022-09-24.
 * @see _772_基本计算器III
 */
public class _770_基本计算器IV{

    class Solution{
        public List<String> basicCalculatorIV(String expression, String[] evalvars, int[] evalints) {
            Map<String, Integer> evalMap = new HashMap();
            for (int i = 0; i < evalvars.length; i++) {
                evalMap.put(evalvars[i], evalints[i]);
            }
            return parse(expression).evaluate(evalMap).toList();
        }

        /**
         * 创造一个新的 Poly实例来表死后常数或 expr指定的变量
         */
        public Poly make(String expr) {
            Poly ans = new Poly();
            List<String> list = new ArrayList();
            if (Character.isDigit(expr.charAt(0))) {
                ans.update(list, Integer.valueOf(expr));
            }
            else {
                list.add(expr);
                ans.update(list, 1);
            }
            return ans;
        }

        /**
         * 返回对左边和右边进行 symbol 操作之后的结果
         */
        public Poly combine(Poly left, Poly right, char symbol) {
            if (symbol == '+') {
                return left.add(right);
            }
            if (symbol == '-') {
                return left.sub(right);
            }
            if (symbol == '*') {
                return left.mul(right);
            }
            throw null;
        }

        /**
         * 将 expr 解析成一个Poly实例
         */
        public Poly parse(String expr) {
            List<Poly> bucket = new ArrayList();
            List<Character> symbols = new ArrayList();
            int i = 0;
            while (i < expr.length()) {
                if (expr.charAt(i) == '(') {
                    int bal = 0, j = i;
                    for (; j < expr.length(); j++) {
                        if (expr.charAt(j) == '(') {
                            bal++;
                        }
                        if (expr.charAt(j) == ')') {
                            bal--;
                        }
                        if (bal == 0) {
                            break;
                        }
                    }
                    bucket.add(parse(expr.substring(i + 1, j)));
                    i = j;
                }
                else if (Character.isLetterOrDigit(expr.charAt(i))) {
                    int j = i;
                    search:
                    {
                        for (; j < expr.length(); j++) {
                            if (expr.charAt(j) == ' ') {
                                bucket.add(make(expr.substring(i, j)));
                                break search;
                            }
                        }
                        bucket.add(make(expr.substring(i)));
                    }
                    i = j;
                }
                else if (expr.charAt(i) != ' ') {
                    symbols.add(expr.charAt(i));
                }
                i++;
            }
            for (int j = symbols.size() - 1; j >= 0; j--) {
                if (symbols.get(j) == '*') {
                    bucket.set(j, combine(bucket.get(j), bucket.remove(j + 1), symbols.remove(j)));
                }
            }
            if (bucket.isEmpty()) {
                return new Poly();
            }
            Poly ans = bucket.get(0);
            for (int j = 0; j < symbols.size(); j++) {
                ans = combine(ans, bucket.get(j + 1), symbols.get(j));
            }
            return ans;
        }


        /**
         * 实现多项式类的一系列操作
         */
        class Poly{
            HashMap<List<String>, Integer> count;

            Poly() {
                count = new HashMap();
            }

            /**
             *
             */
            void update(List<String> key, int val) {
                this.count.put(key, this.count.getOrDefault(key, 0) + val);

            }

            /**
             * 返回this + that 的结果
             */
            Poly add(Poly that) {
                Poly ans = new Poly();
                for (List<String> k : this.count.keySet()) {
                    ans.update(k, this.count.get(k));
                }
                for (List<String> k : that.count.keySet()) {
                    ans.update(k, that.count.get(k));
                }
                return ans;
            }

            /**
             * 返回this - that 的结果
             */
            Poly sub(Poly that) {
                Poly ans = new Poly();
                for (List<String> k : this.count.keySet()) {
                    ans.update(k, this.count.get(k));
                }
                for (List<String> k : that.count.keySet()) {
                    ans.update(k, -that.count.get(k));
                }
                return ans;
            }

            /**
             * 返回this * that 的结果
             */
            Poly mul(Poly that) {
                Poly ans = new Poly();
                for (List<String> k1 : this.count.keySet()) {
                    for (List<String> k2 : that.count.keySet()) {
                        List<String> kNew = new ArrayList<>();
                        for (String x : k1) {
                            kNew.add(x);
                        }
                        for (String x : k2) {
                            kNew.add(x);
                        }
                        Collections.sort(kNew);
                        ans.update(kNew, this.count.get(k1) * that.count.get(k2));
                    }
                }

                return ans;
            }

            /**
             * 返回将所有的自由变量替换成 evalMap 指定常数之后的结果
             */
            Poly evaluate(Map<String, Integer> evalMap) {
                Poly ans = new Poly();
                for (List<String> k : this.count.keySet()) {
                    int c = this.count.get(k);
                    List<String> free = new ArrayList();
                    for (String token : k) {
                        if (evalMap.containsKey(token)) {
                            c *= evalMap.get(token);
                        }
                        else {
                            free.add(token);
                        }
                    }
                    ans.update(free, c);
                }
                return ans;
            }

            int compareList(List<String> A, List<String> B) {
                int i = 0;
                for (String x : A) {
                    String y = B.get(i++);
                    if (x.compareTo(y) != 0) {
                        return x.compareTo(y);
                    }
                }
                return 0;
            }

            /**
             * 返回正确的多项式输出格式
             */
            List<String> toList() {
                List<String> ans = new ArrayList();
                List<List<String>> keys = new ArrayList(this.count.keySet());
                Collections.sort(keys, (a, b) -> {
                    return a.size() != b.size() ? b.size() - a.size() :compareList(a, b);
                });
                for (List<String> key : keys) {
                    int v = this.count.get(key);
                    if (v == 0) {
                        continue;
                    }
                    StringBuilder word = new StringBuilder();
                    word.append("" + v);
                    for (String token : key) {
                        word.append('*');
                        word.append(token);
                    }
                    ans.add(word.toString());
                }
                return ans;

            }

        }
    }


    public class Solution2{

        public List<String> basicCalculatorIV(String expression, String[] evalvars, int[] evalints) {
            class Item implements Comparable<Item>{
                int coeff;
                private ArrayList<String> factors;

                private Item(String factor, int coeff) {
                    this.factors = new ArrayList<>();
                    this.factors.add(factor);
                    this.coeff = coeff;
                }

                private Item(int coeff) {
                    this.factors = new ArrayList<>();
                    this.coeff = coeff;
                }

                private Item() {
                    this.factors = new ArrayList<>();
                    this.coeff = 0;
                }

                @Override
                public int compareTo(Item item) {
                    if (this.factors.size() == item.factors.size()) {
                        int index = 0;
                        while (index < factors.size() && this.factors.get(index).equals(item.factors.get(index))) {
                            index += 1;
                        }
                        return (index == factors.size()) ? 0 :this.factors.get(index).compareTo(item.factors.get(index));
                    }
                    else {
                        return item.factors.size() - this.factors.size();
                    }
                }

                @Override
                public String toString() {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(coeff);
                    for (String factor : factors) {
                        stringBuilder.append("*").append(factor);
                    }
                    return stringBuilder.toString();
                }

                Item mul(Item item) {
                    Item result = new Item();
                    result.coeff = this.coeff * item.coeff;
                    result.factors.addAll(this.factors);
                    result.factors.addAll(item.factors);
                    result.factors.sort(String::compareTo);
                    return result;
                }
            }

            class Expr{
                private ArrayList<Item> items;

                private Expr(Item item) {
                    this.items = new ArrayList<>();
                    this.items.add(item);
                }

                private void add(Expr expr) {
                    items.addAll(expr.items);
                    items.sort(Item::compareTo);
                    clean();
                }

                private void mul(Expr expr) {
                    ArrayList<Item> result = new ArrayList<>();
                    for (Item item1 : items) {
                        for (Item item2 : expr.items) {
                            result.add(item1.mul(item2));
                        }
                    }
                    items = result;
                    items.sort(Item::compareTo);
                    clean();
                }

                private void clean() {
                    for (int i = 0; i < items.size(); i++) {
                        while (i + 1 < items.size() && items.get(i).compareTo(items.get(i + 1)) == 0) {
                            items.get(i).coeff += items.get(i + 1).coeff;
                            items.remove(i + 1);
                        }
                        if (i < items.size() && items.get(i).coeff == 0) {
                            items.remove(i--);
                        }
                    }
                }

                private Expr operate(Expr expr, String op) {
                    switch (op){
                        case "+":
                            add(expr);
                            break;
                        case "-":
                            for (Item item : expr.items) {
                                item.coeff *= -1;
                            }
                            add(expr);
                            break;
                        case "*":
                            mul(expr);
                            break;
                    }
                    return this;
                }
            }

            HashMap<String, Integer> map = new HashMap<>();
            for (int i = 0; i < evalvars.length; i++) {
                map.put(evalvars[i], evalints[i]);
            }

            LinkedList<Expr> mainStack = new LinkedList<>();
            LinkedList<String> symStack = new LinkedList<>();
            int index = 0;
            while (index < expression.length()) {
                if (expression.charAt(index) == ' ') {
                    index += 1;
                }
                else if (expression.charAt(index) >= '0' && expression.charAt(index) <= '9') {
                    int x = 0;
                    while (index < expression.length() && expression.charAt(index) >= '0' && expression.charAt(index) <= '9') {
                        x = x * 10 + expression.charAt(index++) - '0';
                    }
                    mainStack.push(new Expr(new Item(x)));
                }
                else if (expression.charAt(index) >= 'a' && expression.charAt(index) <= 'z') {
                    StringBuilder stringBuilder = new StringBuilder();
                    while (index < expression.length() && expression.charAt(index) >= 'a' && expression.charAt(index) <= 'z') {
                        stringBuilder.append(expression.charAt(index++));
                    }
                    String factor = stringBuilder.toString();
                    if (map.containsKey(factor)) {
                        mainStack.push(new Expr(new Item(map.get(factor))));
                    }
                    else {
                        mainStack.push(new Expr(new Item(stringBuilder.toString(), 1)));
                    }
                }
                else if (expression.charAt(index) == '(') {
                    symStack.push("(");
                    index += 1;
                }
                else if (expression.charAt(index) == ')') {
                    while (!symStack.isEmpty() && !symStack.peek().equals("(")) {
                        Expr expr2 = mainStack.pop();
                        Expr expr1 = mainStack.pop();
                        mainStack.push(expr1.operate(expr2, symStack.pop()));

                    }
                    symStack.pop();
                    index += 1;
                }
                else if (expression.charAt(index) == '*') {
                    while (!symStack.isEmpty() && symStack.peek().equals("*")) {
                        Expr expr2 = mainStack.pop();
                        Expr expr1 = mainStack.pop();
                        mainStack.push(expr1.operate(expr2, symStack.pop()));
                    }
                    symStack.push("*");
                    index += 1;
                }
                else {
                    while (!symStack.isEmpty() && (symStack.peek().equals("+") || symStack.peek().equals("-") || symStack.peek().equals("*"))) {
                        Expr expr2 = mainStack.pop();
                        Expr expr1 = mainStack.pop();
                        mainStack.push(expr1.operate(expr2, symStack.pop()));
                    }
                    symStack.push((expression.charAt(index) == '+') ? "+" :"-");
                    index += 1;
                }
            }
            while (!symStack.isEmpty()) {
                Expr expr2 = mainStack.pop();
                Expr expr1 = mainStack.pop();
                mainStack.push(expr1.operate(expr2, symStack.pop()));
            }

            ArrayList<String> result = new ArrayList<>();
            Expr expr = mainStack.pop();
            expr.clean();
            for (Item item : expr.items) {
                result.add(item.toString());
            }
            return result;
        }
    }


}
