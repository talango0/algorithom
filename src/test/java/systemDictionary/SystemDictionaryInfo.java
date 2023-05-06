package systemDictionary;

import sun.jvm.hotspot.memory.SystemDictionary;
import sun.jvm.hotspot.oops.InstanceKlass;
import sun.jvm.hotspot.oops.Klass;
import sun.jvm.hotspot.runtime.VM;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author mayanwei
 * @date 2023-04-15.
 */
public class SystemDictionaryInfo{
    private static void listInstanceClass() {
        SystemDictionary systemDictionary = VM.getVM().getSystemDictionary();
        List<InstanceKlass> instanceKlasses = new ArrayList<>();
        systemDictionary.allClassesDo(new SystemDictionary.ClassVisitor(){
            @Override
            public void visit(Klass klass) {
                if (klass instanceof InstanceKlass) {
                    instanceKlasses.add((InstanceKlass) klass);
                }
            }
        });

        Collections.sort(instanceKlasses, new Comparator<InstanceKlass>(){
            @Override
            public int compare(InstanceKlass o1, InstanceKlass o2) {
                return o1.getName().toString().compareTo(o2.getName().toString());
            }
        });

        for (InstanceKlass instanceKlass : instanceKlasses) {
            System.out.println(instanceKlass);
        }
    }
    public static void main(String[] args) {
        listInstanceClass();

    }

}
