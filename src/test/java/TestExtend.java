import org.junit.Test;

public class TestExtend {

    static class Animals{
        private String eat;
        private String run;

        public String getEat() {
            return eat;
        }

        public void setEat(String eat) {
            this.eat = eat;
        }

        public String getRun() {
            return run;
        }

        public void setRun(String run) {
            this.run = run;
        }
    }
    static class Dog extends Animals{
        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static void main(String[] args) {

        Dog dog = new Dog();
        System.out.println(dog.getRun());
        Animals animal = new  Animals();

        System.out.println(animal.getRun());


    }


    /**
     * sum = 1 +2 + 3 + 4 + ... + 100
     */

    @Test
    public void testRecursive(){
        System.out.println(sum(5));
    }
    public Integer sum(Integer i){
        if(i>1){
            return i+sum(i-1);
        }
        return 1;
    }

}
