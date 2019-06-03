package com.example.regexp;

import java.util.regex.Pattern;

/**
 * Created by YanYadi on 2017/8/14.
 */
public class MatchString {
    public static void main(String[] args) {
        String string = "var flashvars={f:'http://video.ums.uc.cn/video/wemedia/004ac28b851d436eaf5f646703b2bd0c/259fae061fde098f3f0cb3aeb763ab56-85386432-2.mp4?auth_key=1492150155-0-0-69f545c9f4288eb17f9dad3b9ebc4435',c:0,loaded:'loadedHandler',p:1};\t\t\n" +
                "\t\tvar params={bgcolor:'#FFF',allowFullScreen:true,allowScriptAccess:'always',wmode:'transparent'};\n" +
                "\t\tCKobject.embedSWF('https://vs.6no.cc/player/player.swf','a1','ckplayer_a1','100%','460px',flashvars,params);\n" +
                "\t\tvar video=['http://video.ums.uc.cn/video/wemedia/004ac28b851d436eaf5f646703b2bd0c/259fae061fde098f3f0cb3aeb763ab56-85386432-2.mp4?auth_key=1492150155-0-0-69f545c9f4288eb17f9dad3b9ebc4435'];\n" +
                "\t\t\tvar support=['iPad','iPhone','ios','android+false','msie10+false'];\n" +
                "\t\tCKobject.embedHTML5('a1','ckplayer_a1','100%','230px',video,flashvars,support);";

        Pattern pattern = Pattern.compile("video", Pattern.MULTILINE |Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

        System.out.println(pattern.matcher(string).find());
        System.out.println(string);
    }
}
