package com.example.designPattern;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by YanYadi on 2017/5/8.
 * ������ģʽ(Filter) ���� ��׼ģʽ��Criteria�����ṹ��ģʽ��
 * ������ϲ�ͬ�Ĳ��ԣ����Ĺ�������
 */
public class Filter {
    public static void main(String[] args) {
        List<Person> allPeople = new ArrayList<>();
        allPeople.add(new Person(Gender.MALE, "����"));
        allPeople.add(new Person(Gender.FEMALE, "�ź���"));
        allPeople.add(new Person(Gender.FEMALE, "�Ű�֦"));
        allPeople.add(new Person(Gender.MALE, "��˼��"));
        allPeople.add(new Person(Gender.MALE, "����"));

        //����
        MeetCriteria<Person> maleCriteria = new CriteriaMale();
        System.out.println("==Male==");
        printPersons(maleCriteria.criteria(allPeople));

        //Ů��
        MeetCriteria<Person> femaleCriteria = new CriteriaFemale();
        System.out.println("==FeMale==");
        printPersons(femaleCriteria.criteria(allPeople));

        //���˻�Ů��
        MeetCriteria<Person> maleAndFemal = new OrCriteria(femaleCriteria, maleCriteria);
        System.out.println("==Male or Female");
        printPersons(maleAndFemal.criteria(allPeople));

    }

    static void printPersons(List<Person> persons) {
        for (Person p : persons) {
            System.out.println("name : " + p.name + "  gender : " + (p.gender == Gender.MALE ? "��" : "Ů"));
        }
    }

    /**
     * Created by YanYadi on 2017/5/8.
     */
    enum Gender {
        MALE, FEMALE
    }

    /**
     * ���� ���� ��Աɸѡ��
     *
     * @param <T>
     */
    interface MeetCriteria<T> {
        List<T> criteria(List<T> list);
    }

    /**
     * ����ɸѡ
     */
    static class CriteriaMale implements MeetCriteria<Person> {

        @Override public List<Person> criteria(List<Person> list) {
            List<Person> males = new ArrayList<>();
            for (Person person : list) {
                if (person.gender == Gender.MALE)
                    males.add(person);
            }
            return males;
        }
    }

    /**
     * Ů��ɸѡ��
     */
    static class CriteriaFemale implements MeetCriteria<Person> {

        @Override public List<Person> criteria(List<Person> list) {
            List<Person> females = new ArrayList<>();
            for (Person person : list) {
                if (person.gender == Gender.FEMALE)
                    females.add(person);
            }
            return females;
        }
    }

    /**
     * �� ����ɸѡ��
     */
    static class OrCriteria implements MeetCriteria<Person> {
        MeetCriteria<Person> criteria;
        MeetCriteria<Person> othersCriteria;

        public OrCriteria(MeetCriteria<Person> criteria, MeetCriteria<Person> othersCriteria) {
            this.criteria = criteria;
            this.othersCriteria = othersCriteria;
        }

        @Override public List<Person> criteria(List<Person> list) {
            List<Person> persons = criteria.criteria(list);
            List<Person> otherPersons = othersCriteria.criteria(list);
            for (Person person : otherPersons) {
                if (!persons.contains(person))
                    persons.add(person);
            }
            return persons;
        }
    }

    /**
     * Created by YanYadi on 2017/5/8.
     */
    static class Person {
        Gender gender;
        String name;
        int age;
        boolean single;

        public Person(Gender gender, String name) {
            this.gender = gender;
            this.name = name;
        }

        public Person(Gender gender, String name, int age, boolean single) {
            this.gender = gender;
            this.name = name;
            this.age = age;
            this.single = single;
        }
    }
}
