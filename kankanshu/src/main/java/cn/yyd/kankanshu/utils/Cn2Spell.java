package cn.yyd.kankanshu.utils;

/**
 * Created by YanYadi on 2017/7/25.
 */
public class Cn2Spell {
    private static int[] pyvalue = new int[] {-20319, -20317, -20304, -20295, -20292, -20283, -20265, -20257, -20242, -20230, -20051, -20036, -20032,
            -20026, -20002, -19990, -19986, -19982, -19976, -19805, -19784, -19775, -19774, -19763, -19756, -19751, -19746, -19741, -19739, -19728,
            -19725, -19715, -19540, -19531, -19525, -19515, -19500, -19484, -19479, -19467, -19289, -19288, -19281, -19275, -19270, -19263, -19261,
            -19249, -19243, -19242, -19238, -19235, -19227, -19224, -19218, -19212, -19038, -19023, -19018, -19006, -19003, -18996, -18977, -18961,
            -18952, -18783, -18774, -18773, -18763, -18756, -18741, -18735, -18731, -18722, -18710, -18697, -18696, -18526, -18518, -18501, -18490,
            -18478, -18463, -18448, -18447, -18446, -18239, -18237, -18231, -18220, -18211, -18201, -18184, -18183, -18181, -18012, -17997, -17988,
            -17970, -17964, -17961, -17950, -17947, -17931, -17928, -17922, -17759, -17752, -17733, -17730, -17721, -17703, -17701, -17697, -17692,
            -17683, -17676, -17496, -17487, -17482, -17468, -17454, -17433, -17427, -17417, -17202, -17185, -16983, -16970, -16942, -16915, -16733,
            -16708, -16706, -16689, -16664, -16657, -16647, -16474, -16470, -16465, -16459, -16452, -16448, -16433, -16429, -16427, -16423, -16419,
            -16412, -16407, -16403, -16401, -16393, -16220, -16216, -16212, -16205, -16202, -16187, -16180, -16171, -16169, -16158, -16155, -15959,
            -15958, -15944, -15933, -15920, -15915, -15903, -15889, -15878, -15707, -15701, -15681, -15667, -15661, -15659, -15652, -15640, -15631,
            -15625, -15454, -15448, -15436, -15435, -15419, -15416, -15408, -15394, -15385, -15377, -15375, -15369, -15363, -15362, -15183, -15180,
            -15165, -15158, -15153, -15150, -15149, -15144, -15143, -15141, -15140, -15139, -15128, -15121, -15119, -15117, -15110, -15109, -14941,
            -14937, -14933, -14930, -14929, -14928, -14926, -14922, -14921, -14914, -14908, -14902, -14894, -14889, -14882, -14873, -14871, -14857,
            -14678, -14674, -14670, -14668, -14663, -14654, -14645, -14630, -14594, -14429, -14407, -14399, -14384, -14379, -14368, -14355, -14353,
            -14345, -14170, -14159, -14151, -14149, -14145, -14140, -14137, -14135, -14125, -14123, -14122, -14112, -14109, -14099, -14097, -14094,
            -14092, -14090, -14087, -14083, -13917, -13914, -13910, -13907, -13906, -13905, -13896, -13894, -13878, -13870, -13859, -13847, -13831,
            -13658, -13611, -13601, -13406, -13404, -13400, -13398, -13395, -13391, -13387, -13383, -13367, -13359, -13356, -13343, -13340, -13329,
            -13326, -13318, -13147, -13138, -13120, -13107, -13096, -13095, -13091, -13076, -13068, -13063, -13060, -12888, -12875, -12871, -12860,
            -12858, -12852, -12849, -12838, -12831, -12829, -12812, -12802, -12607, -12597, -12594, -12585, -12556, -12359, -12346, -12320, -12300,
            -12120, -12099, -12089, -12074, -12067, -12058, -12039, -11867, -11861, -11847, -11831, -11798, -11781, -11604, -11589, -11536, -11358,
            -11340, -11339, -11324, -11303, -11097, -11077, -11067, -11055, -11052, -11045, -11041, -11038, -11024, -11020, -11019, -11018, -11014,
            -10838, -10832, -10815, -10800, -10790, -10780, -10764, -10587, -10544, -10533, -10519, -10331, -10329, -10328, -10322, -10315, -10309,
            -10307, -10296, -10281, -10274, -10270, -10262, -10260, -10256, -10254};
    public static String[] pystr = new String[] {"a", "ai", "an", "ang", "ao", "ba", "bai", "ban", "bang", "bao", "bei", "ben", "beng", "bi", "bian",
            "biao", "bie", "bin", "bing", "bo", "bu", "ca", "cai", "can", "cang", "cao", "ce", "ceng", "cha", "chai", "chan", "chang", "chao", "che",
            "chen", "cheng", "chi", "chong", "chou", "chu", "chuai", "chuan", "chuang", "chui", "chun", "chuo", "ci", "cong", "cou", "cu", "cuan",
            "cui", "cun", "cuo", "da", "dai", "dan", "dang", "dao", "de", "deng", "di", "dian", "diao", "die", "ding", "diu", "dong", "dou", "du",
            "duan", "dui", "dun", "duo", "e", "en", "er", "fa", "fan", "fang", "fei", "fen", "feng", "fo", "fou", "fu", "ga", "gai", "gan", "gang",
            "gao", "ge", "gei", "gen", "geng", "gong", "gou", "gu", "gua", "guai", "guan", "guang", "gui", "gun", "guo", "ha", "hai", "han", "hang",
            "hao", "he", "hei", "hen", "heng", "hong", "hou", "hu", "hua", "huai", "huan", "huang", "hui", "hun", "huo", "ji", "jia", "jian",
            "jiang", "jiao", "jie", "jin", "jing", "jiong", "jiu", "ju", "juan", "jue", "jun", "ka", "kai", "kan", "kang", "kao", "ke", "ken",
            "keng", "kong", "kou", "ku", "kua", "kuai", "kuan", "kuang", "kui", "kun", "kuo", "la", "lai", "lan", "lang", "lao", "le", "lei", "leng",
            "li", "lia", "lian", "liang", "liao", "lie", "lin", "ling", "liu", "long", "lou", "lu", "lv", "luan", "lue", "lun", "luo", "ma", "mai",
            "man", "mang", "mao", "me", "mei", "men", "meng", "mi", "mian", "miao", "mie", "min", "ming", "miu", "mo", "mou", "mu", "na", "nai",
            "nan", "nang", "nao", "ne", "nei", "nen", "neng", "ni", "nian", "niang", "niao", "nie", "nin", "ning", "niu", "nong", "nu", "nv", "nuan",
            "nue", "nuo", "o", "ou", "pa", "pai", "pan", "pang", "pao", "pei", "pen", "peng", "pi", "pian", "piao", "pie", "pin", "ping", "po", "pu",
            "qi", "qia", "qian", "qiang", "qiao", "qie", "qin", "qing", "qiong", "qiu", "qu", "quan", "que", "qun", "ran", "rang", "rao", "re",
            "ren", "reng", "ri", "rong", "rou", "ru", "ruan", "rui", "run", "ruo", "sa", "sai", "san", "sang", "sao", "se", "sen", "seng", "sha",
            "shai", "shan", "shang", "shao", "she", "shen", "sheng", "shi", "shou", "shu", "shua", "shuai", "shuan", "shuang", "shui", "shun",
            "shuo", "si", "song", "sou", "su", "suan", "sui", "sun", "suo", "ta", "tai", "tan", "tang", "tao", "te", "teng", "ti", "tian", "tiao",
            "tie", "ting", "tong", "tou", "tu", "tuan", "tui", "tun", "tuo", "wa", "wai", "wan", "wang", "wei", "wen", "weng", "wo", "wu", "xi",
            "xia", "xian", "xiang", "xiao", "xie", "xin", "xing", "xiong", "xiu", "xu", "xuan", "xue", "xun", "ya", "yan", "yang", "yao", "ye", "yi",
            "yin", "ying", "yo", "yong", "you", "yu", "yuan", "yue", "yun", "za", "zai", "zan", "zang", "zao", "ze", "zei", "zen", "zeng", "zha",
            "zhai", "zhan", "zhang", "zhao", "zhe", "zhen", "zheng", "zhi", "zhong", "zhou", "zhu", "zhua", "zhuai", "zhuan", "zhuang", "zhui",
            "zhun", "zhuo", "zi", "zong", "zou", "zu", "zuan", "zui", "zun", "zuo"};
    private StringBuilder buffer;
    private static Cn2Spell cn2Spell= new Cn2Spell ();
    public static Cn2Spell getInstance() {
        return cn2Spell;
    }
    // 汉字转成ASCII码
    private int getChsAscii(String chs) {
        int asc = 0;
        try {
            byte[] bytes = chs.getBytes("gbk");
            if (bytes == null || bytes.length > 2 || bytes.length <= 0) {
                throw new RuntimeException("illegal resource string");
            }
            if (bytes.length == 1) {
                asc = bytes[0];
            }
            if (bytes.length == 2) {
                int hightByte = 256 + bytes[0];
                int lowByte = 256 + bytes[1];
                asc = (256 * hightByte + lowByte) - 256 * 256;
            }
        } catch (Exception e) {
            System.out.println("ERROR:ChineseSpelling.class-getChsAscii(String chs)" + e);
        }
        return asc;
    }
    // 单字解析
    public String convert(String str) {
        String result = null;
        int ascii = getChsAscii(str);
        if (ascii > 0 && ascii < 160) {
            result = String.valueOf((char) ascii);
        } else {
            for (int i = (pyvalue.length - 1); i >= 0; i--) {
                if (pyvalue[i] <= ascii) {
                    result = pystr[i];
                    break;
                }
            }
        }
        return result;
    }
    // 词组解析
    public String getSelling(String chs) {
        String key, value;
        buffer = new StringBuilder();
        for (int i = 0; i < chs.length(); i++) {
            key = chs.substring(i, i + 1);
            if (key.getBytes().length >= 2) {
                value = (String) convert(key);
                if (value == null) {
                    value = "unknown";
                }
            } else {
                value = key;
            }
            buffer.append(value);
        }
        return buffer.toString();
    }

    static String string = "{\"code\":10000,\"msg\":\"成功\",\"data\":{\"brand_lists\":[{\"brand_id\":\"11\",\"brand_name\":\"珍宝百库\",\"brand_app_image\":\"/images/goods/20170523/1495509239app.jpg\",\"variety_image\":\"http://static.zhenbaobaiku.com//images/goods/20170523/1495509239app.jpg\",\"variety_id\":\"11\",\"variety_name\":\"珍宝百库\"},{\"brand_id\":\"8\",\"brand_name\":\"Precious Koo\",\"brand_app_image\":\"/images/goods/20161217/1481956432app.png\",\"variety_image\":\"http://static.zhenbaobaiku.com//images/goods/20161217/1481956432app.png\",\"variety_id\":\"8\",\"variety_name\":\"Precious Koo\"},{\"brand_id\":\"7\",\"brand_name\":\"Baidu\",\"brand_app_image\":\"/images/goods/20161217/1481956432app.png\",\"variety_image\":\"http://static.zhenbaobaiku.com//images/goods/20161217/1481956432app.png\",\"variety_id\":\"8\",\"variety_name\":\"Baidu\"},{\"brand_id\":\"6\",\"brand_name\":\"Nick\",\"brand_app_image\":\"/images/goods/20161217/1481956432app.png\",\"variety_image\":\"http://static.zhenbaobaiku.com//images/goods/20161217/1481956432app.png\",\"variety_id\":\"8\",\"variety_name\":\"Nick\"},{\"brand_id\":\"5\",\"brand_name\":\"王麻子\",\"brand_app_image\":\"/images/goods/20161217/1481956432app.png\",\"variety_image\":\"http://static.zhenbaobaiku.com//images/goods/20161217/1481956432app.png\",\"variety_id\":\"8\",\"variety_name\":\"王麻子\"},{\"brand_id\":\"3\",\"brand_name\":\"He\",\"brand_app_image\":\"/images/goods/20161217/1481956432app.png\",\"variety_image\":\"http://static.zhenbaobaiku.com//images/goods/20161217/1481956432app.png\",\"variety_id\":\"8\",\"variety_name\":\"He\"}],\"variety_lists\":[{\"variety_id\":\"1\",\"variety_name\":\"降真香\",\"variety_description\":\"降真香\",\"variety_image\":\"http://static.zhenbaobaiku.com//images/goods/20161217/1481953589app.png\",\"variety_open\":\"1\",\"types\":[{\"goods_type_id\":\"2\",\"goods_type_name\":\"手串\",\"goods_type_image\":\"http://static.zhenbaobaiku.com//images/goods/20161217/1481955384.png\"},{\"goods_type_id\":\"16\",\"goods_type_name\":\"雕件\",\"goods_type_image\":\"http://static.zhenbaobaiku.com//images/goods/20161103/1478106648.png\"}],\"goodsLists\":[]},{\"variety_id\":\"5\",\"variety_name\":\"珠宝首饰\",\"variety_description\":\"\",\"variety_image\":\"http://static.zhenbaobaiku.com//images/goods/20161228/1482916171app.png\",\"variety_open\":\"1\",\"types\":[{\"goods_type_id\":\"3\",\"goods_type_name\":\"钻石\",\"goods_type_image\":\"http://static.zhenbaobaiku.com//images/goods/20170605/1496649019.png\"},{\"goods_type_id\":\"4\",\"goods_type_name\":\"水晶\",\"goods_type_image\":\"http://static.zhenbaobaiku.com//images/goods/20161217/1481955468.png\"},{\"goods_type_id\":\"6\",\"goods_type_name\":\"裸钻\",\"goods_type_image\":\"http://static.zhenbaobaiku.com//images/goods/20161217/1481955484.png\"},{\"goods_type_id\":\"17\",\"goods_type_name\":\"水晶佛像\",\"goods_type_image\":\"http://static.zhenbaobaiku.com//images/goods/20161103/1478106745.png\"},{\"goods_type_id\":\"22\",\"goods_type_name\":\"红宝石\",\"goods_type_image\":\"http://static.zhenbaobaiku.com//images/goods/20170605/1496648942.png\"},{\"goods_type_id\":\"23\",\"goods_type_name\":\"彩色钻石\",\"goods_type_image\":\"http://static.zhenbaobaiku.com//images/goods/20170605/1496648973.png\"},{\"goods_type_id\":\"24\",\"goods_type_name\":\"碧玺\",\"goods_type_image\":\"http://static.zhenbaobaiku.com//images/goods/20170605/1496648996.png\"}],\"goodsLists\":[]},{\"variety_id\":\"7\",\"variety_name\":\"书籍\",\"variety_description\":\"\",\"variety_image\":\"http://static.zhenbaobaiku.com//images/goods/20161217/1481953658app.png\",\"variety_open\":\"0\",\"types\":[{\"goods_type_id\":\"18\",\"goods_type_name\":\"沉香\",\"goods_type_image\":\"http://static.zhenbaobaiku.com//images/goods/20161217/1481954211.png\"}],\"goodsLists\":[]},{\"variety_id\":\"9\",\"variety_name\":\"配件\",\"variety_description\":\"各种饰品配件\",\"variety_image\":\"http://static.zhenbaobaiku.com//images/goods/20161217/1481953770app.png\",\"variety_open\":\"0\",\"types\":[{\"goods_type_id\":\"7\",\"goods_type_name\":\"隔珠\",\"goods_type_image\":\"http://static.zhenbaobaiku.com//images/goods/20161025/1477389621.png\"},{\"goods_type_id\":\"8\",\"goods_type_name\":\"隔片\",\"goods_type_image\":\"http://static.zhenbaobaiku.com//images/goods/20161025/1477389645.png\"},{\"goods_type_id\":\"9\",\"goods_type_name\":\"佛头\",\"goods_type_image\":\"http://static.zhenbaobaiku.com//images/goods/20161025/1477389663.png\"},{\"goods_type_id\":\"10\",\"goods_type_name\":\"吊坠\",\"goods_type_image\":\"http://static.zhenbaobaiku.com//images/goods/20161025/1477389674.png\"},{\"goods_type_id\":\"11\",\"goods_type_name\":\"背云\",\"goods_type_image\":\"http://static.zhenbaobaiku.com//images/goods/20161025/1477389692.png\"},{\"goods_type_id\":\"19\",\"goods_type_name\":\"配线\",\"goods_type_image\":\"http://static.zhenbaobaiku.com//images/goods/20161025/1477389517.png\"},{\"goods_type_id\":\"20\",\"goods_type_name\":\"铃杵\",\"goods_type_image\":\"http://static.zhenbaobaiku.com//images/goods/20161025/1477389593.png\"}],\"goodsLists\":[]}],\"apply_image\":\"http://static.zhenbaobaiku.com/app/images/shougongyuyue.png?t=1500959133\"},\"time\":1500959133}";
    public static void main(String[] args) {
        System.out.println(Integer.toHexString( string.getBytes().length) );
    }
}
