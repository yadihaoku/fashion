// ICalculator.aidl
package cn.yyd.aidlserver;

import cn.yyd.aidlserver.Person;

interface ICalculator {
    String addition(int one, int two);

    void sleep(int timeInMillisecond);


    Person makePerson();

    oneway void getCurrentMillisecond();

    List<String> getNames();

    Map getParams(String prefix);

    void mixed(in Person p);
    void setAge(out Person p);
    void reset(inout Person p);

    long getMyPid();

}
