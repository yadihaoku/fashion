package cn.yyd.aidlserver;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by YanYadi on 2017/3/15.
 */
public class Person implements Parcelable {
    private String name;
    private int age;
    private String description;

    public Person(){
    }
    protected Person(Parcel in) {
        name = in.readString();
        age = in.readInt();
        description = in.readString();
    }

    public Person(String n, int age, String desc) {
        name = n;
        this.age = age;
        description = desc;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void readFromParcel(Parcel in) {
        name = in.readString();
        age = in.readInt();
        description = in.readString();
    }

    @Override
    public String toString() {
        return  new StringBuilder("name :").append(name).append("\nage :").append(age).append("\ndes : ").append(description).toString();
    }
}
