package com.mcxtzhang.rxjava2demo.retrofit.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2017/1/5.
 */

public class RawMovieBean {
    @Override
    public String toString() {
        return "RawMovieBean{" +
                "count=" + count +
                ", start=" + start +
                ", total=" + total +
                ", title='" + title + '\'' +
                ", subjects=" + subjects +
                '}';
    }

    /**
     * count : 10
     * start : 0
     * total : 250
     * subjects : [{"rating":{"max":10,"average":9.6,"stars":"50","min":0},"genres":["犯罪","剧情"],"title":"肖申克的救赎","casts":[{"alt":"https://movie.douban.com/celebrity/1054521/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/17525.jpg","large":"https://img3.doubanio.com/img/celebrity/large/17525.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/17525.jpg"},"name":"蒂姆·罗宾斯","id":"1054521"},{"alt":"https://movie.douban.com/celebrity/1054534/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/34642.jpg","large":"https://img3.doubanio.com/img/celebrity/large/34642.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/34642.jpg"},"name":"摩根·弗里曼","id":"1054534"},{"alt":"https://movie.douban.com/celebrity/1041179/","avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/5837.jpg","large":"https://img1.doubanio.com/img/celebrity/large/5837.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/5837.jpg"},"name":"鲍勃·冈顿","id":"1041179"}],"collect_count":1003335,"original_title":"The Shawshank Redemption","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1047973/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/230.jpg","large":"https://img3.doubanio.com/img/celebrity/large/230.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/230.jpg"},"name":"弗兰克·德拉邦特","id":"1047973"}],"year":"1994","images":{"small":"https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p480747492.jpg","large":"https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p480747492.jpg","medium":"https://img3.doubanio.com/view/movie_poster_cover/spst/public/p480747492.jpg"},"alt":"https://movie.douban.com/subject/1292052/","id":"1292052"},{"rating":{"max":10,"average":9.4,"stars":"50","min":0},"genres":["剧情","动作","犯罪"],"title":"这个杀手不太冷","casts":[{"alt":"https://movie.douban.com/celebrity/1025182/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/8833.jpg","large":"https://img3.doubanio.com/img/celebrity/large/8833.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/8833.jpg"},"name":"让·雷诺","id":"1025182"},{"alt":"https://movie.douban.com/celebrity/1054454/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/2274.jpg","large":"https://img3.doubanio.com/img/celebrity/large/2274.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/2274.jpg"},"name":"娜塔莉·波特曼","id":"1054454"},{"alt":"https://movie.douban.com/celebrity/1010507/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/104.jpg","large":"https://img3.doubanio.com/img/celebrity/large/104.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/104.jpg"},"name":"加里·奥德曼","id":"1010507"}],"collect_count":970963,"original_title":"Léon","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1031876/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/33301.jpg","large":"https://img3.doubanio.com/img/celebrity/large/33301.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/33301.jpg"},"name":"吕克·贝松","id":"1031876"}],"year":"1994","images":{"small":"https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p511118051.jpg","large":"https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p511118051.jpg","medium":"https://img3.doubanio.com/view/movie_poster_cover/spst/public/p511118051.jpg"},"alt":"https://movie.douban.com/subject/1295644/","id":"1295644"},{"rating":{"max":10,"average":9.5,"stars":"50","min":0},"genres":["剧情","爱情","同性"],"title":"霸王别姬","casts":[{"alt":"https://movie.douban.com/celebrity/1003494/","avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/67.jpg","large":"https://img1.doubanio.com/img/celebrity/large/67.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/67.jpg"},"name":"张国荣","id":"1003494"},{"alt":"https://movie.douban.com/celebrity/1050265/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/10381.jpg","large":"https://img3.doubanio.com/img/celebrity/large/10381.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/10381.jpg"},"name":"张丰毅","id":"1050265"},{"alt":"https://movie.douban.com/celebrity/1035641/","avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/1399268395.47.jpg","large":"https://img1.doubanio.com/img/celebrity/large/1399268395.47.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/1399268395.47.jpg"},"name":"巩俐","id":"1035641"}],"collect_count":703527,"original_title":"霸王别姬","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1023040/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/750.jpg","large":"https://img3.doubanio.com/img/celebrity/large/750.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/750.jpg"},"name":"陈凯歌","id":"1023040"}],"year":"1993","images":{"small":"https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p1910813120.jpg","large":"https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p1910813120.jpg","medium":"https://img3.doubanio.com/view/movie_poster_cover/spst/public/p1910813120.jpg"},"alt":"https://movie.douban.com/subject/1291546/","id":"1291546"},{"rating":{"max":10,"average":9.4,"stars":"50","min":0},"genres":["剧情","爱情"],"title":"阿甘正传","casts":[{"alt":"https://movie.douban.com/celebrity/1054450/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/551.jpg","large":"https://img3.doubanio.com/img/celebrity/large/551.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/551.jpg"},"name":"汤姆·汉克斯","id":"1054450"},{"alt":"https://movie.douban.com/celebrity/1002676/","avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/51737.jpg","large":"https://img1.doubanio.com/img/celebrity/large/51737.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/51737.jpg"},"name":"罗宾·怀特","id":"1002676"},{"alt":"https://movie.douban.com/celebrity/1031848/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/1345.jpg","large":"https://img3.doubanio.com/img/celebrity/large/1345.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/1345.jpg"},"name":"加里·西尼斯","id":"1031848"}],"collect_count":871494,"original_title":"Forrest Gump","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1053564/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/505.jpg","large":"https://img3.doubanio.com/img/celebrity/large/505.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/505.jpg"},"name":"罗伯特·泽米吉斯","id":"1053564"}],"year":"1994","images":{"small":"https://img1.doubanio.com/view/movie_poster_cover/ipst/public/p510876377.jpg","large":"https://img1.doubanio.com/view/movie_poster_cover/lpst/public/p510876377.jpg","medium":"https://img1.doubanio.com/view/movie_poster_cover/spst/public/p510876377.jpg"},"alt":"https://movie.douban.com/subject/1292720/","id":"1292720"},{"rating":{"max":10,"average":9.5,"stars":"50","min":0},"genres":["剧情","喜剧","爱情"],"title":"美丽人生","casts":[{"alt":"https://movie.douban.com/celebrity/1041004/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/26764.jpg","large":"https://img3.doubanio.com/img/celebrity/large/26764.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/26764.jpg"},"name":"罗伯托·贝尼尼","id":"1041004"},{"alt":"https://movie.douban.com/celebrity/1000375/","avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/9548.jpg","large":"https://img1.doubanio.com/img/celebrity/large/9548.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/9548.jpg"},"name":"尼可莱塔·布拉斯基","id":"1000375"},{"alt":"https://movie.douban.com/celebrity/1000368/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/45590.jpg","large":"https://img3.doubanio.com/img/celebrity/large/45590.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/45590.jpg"},"name":"乔治·坎塔里尼","id":"1000368"}],"collect_count":477902,"original_title":"La vita è bella","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1041004/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/26764.jpg","large":"https://img3.doubanio.com/img/celebrity/large/26764.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/26764.jpg"},"name":"罗伯托·贝尼尼","id":"1041004"}],"year":"1997","images":{"small":"https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p510861873.jpg","large":"https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p510861873.jpg","medium":"https://img3.doubanio.com/view/movie_poster_cover/spst/public/p510861873.jpg"},"alt":"https://movie.douban.com/subject/1292063/","id":"1292063"},{"rating":{"max":10,"average":9.2,"stars":"50","min":0},"genres":["剧情","动画","奇幻"],"title":"千与千寻","casts":[{"alt":"https://movie.douban.com/celebrity/1023337/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/1463193210.13.jpg","large":"https://img3.doubanio.com/img/celebrity/large/1463193210.13.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/1463193210.13.jpg"},"name":"柊瑠美","id":"1023337"},{"alt":"https://movie.douban.com/celebrity/1005438/","avatars":{"small":"https://img5.doubanio.com/img/celebrity/small/44986.jpg","large":"https://img5.doubanio.com/img/celebrity/large/44986.jpg","medium":"https://img5.doubanio.com/img/celebrity/medium/44986.jpg"},"name":"入野自由","id":"1005438"},{"alt":"https://movie.douban.com/celebrity/1045797/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/18785.jpg","large":"https://img3.doubanio.com/img/celebrity/large/18785.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/18785.jpg"},"name":"夏木真理","id":"1045797"}],"collect_count":777680,"original_title":"千と千尋の神隠し","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1054439/","avatars":{"small":"https://img5.doubanio.com/img/celebrity/small/616.jpg","large":"https://img5.doubanio.com/img/celebrity/large/616.jpg","medium":"https://img5.doubanio.com/img/celebrity/medium/616.jpg"},"name":"宫崎骏","id":"1054439"}],"year":"2001","images":{"small":"https://img5.doubanio.com/view/movie_poster_cover/ipst/public/p1910830216.jpg","large":"https://img5.doubanio.com/view/movie_poster_cover/lpst/public/p1910830216.jpg","medium":"https://img5.doubanio.com/view/movie_poster_cover/spst/public/p1910830216.jpg"},"alt":"https://movie.douban.com/subject/1291561/","id":"1291561"},{"rating":{"max":10,"average":9.4,"stars":"50","min":0},"genres":["剧情","历史","战争"],"title":"辛德勒的名单","casts":[{"alt":"https://movie.douban.com/celebrity/1031220/","avatars":{"small":"https://img5.doubanio.com/img/celebrity/small/44906.jpg","large":"https://img5.doubanio.com/img/celebrity/large/44906.jpg","medium":"https://img5.doubanio.com/img/celebrity/medium/44906.jpg"},"name":"连姆·尼森","id":"1031220"},{"alt":"https://movie.douban.com/celebrity/1054393/","avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/1374649659.58.jpg","large":"https://img1.doubanio.com/img/celebrity/large/1374649659.58.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/1374649659.58.jpg"},"name":"本·金斯利","id":"1054393"},{"alt":"https://movie.douban.com/celebrity/1006956/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/28941.jpg","large":"https://img3.doubanio.com/img/celebrity/large/28941.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/28941.jpg"},"name":"拉尔夫·费因斯","id":"1006956"}],"collect_count":457998,"original_title":"Schindler's List","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1054440/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/34602.jpg","large":"https://img3.doubanio.com/img/celebrity/large/34602.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/34602.jpg"},"name":"史蒂文·斯皮尔伯格","id":"1054440"}],"year":"1993","images":{"small":"https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p492406163.jpg","large":"https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p492406163.jpg","medium":"https://img3.doubanio.com/view/movie_poster_cover/spst/public/p492406163.jpg"},"alt":"https://movie.douban.com/subject/1295124/","id":"1295124"},{"rating":{"max":10,"average":9.2,"stars":"45","min":0},"genres":["剧情","爱情","灾难"],"title":"泰坦尼克号","casts":[{"alt":"https://movie.douban.com/celebrity/1041029/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/470.jpg","large":"https://img3.doubanio.com/img/celebrity/large/470.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/470.jpg"},"name":"莱昂纳多·迪卡普里奥","id":"1041029"},{"alt":"https://movie.douban.com/celebrity/1054446/","avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/53358.jpg","large":"https://img1.doubanio.com/img/celebrity/large/53358.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/53358.jpg"},"name":"凯特·温丝莱特","id":"1054446"},{"alt":"https://movie.douban.com/celebrity/1031864/","avatars":{"small":"https://img5.doubanio.com/img/celebrity/small/45186.jpg","large":"https://img5.doubanio.com/img/celebrity/large/45186.jpg","medium":"https://img5.doubanio.com/img/celebrity/medium/45186.jpg"},"name":"比利·赞恩","id":"1031864"}],"collect_count":801055,"original_title":"Titanic","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1022571/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/33715.jpg","large":"https://img3.doubanio.com/img/celebrity/large/33715.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/33715.jpg"},"name":"詹姆斯·卡梅隆","id":"1022571"}],"year":"1997","images":{"small":"https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p457760035.jpg","large":"https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p457760035.jpg","medium":"https://img3.doubanio.com/view/movie_poster_cover/spst/public/p457760035.jpg"},"alt":"https://movie.douban.com/subject/1292722/","id":"1292722"},{"rating":{"max":10,"average":9.2,"stars":"50","min":0},"genres":["剧情","动作","科幻"],"title":"盗梦空间","casts":[{"alt":"https://movie.douban.com/celebrity/1041029/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/470.jpg","large":"https://img3.doubanio.com/img/celebrity/large/470.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/470.jpg"},"name":"莱昂纳多·迪卡普里奥","id":"1041029"},{"alt":"https://movie.douban.com/celebrity/1101703/","avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/3517.jpg","large":"https://img1.doubanio.com/img/celebrity/large/3517.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/3517.jpg"},"name":"约瑟夫·高登-莱维特","id":"1101703"},{"alt":"https://movie.douban.com/celebrity/1012520/","avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/118.jpg","large":"https://img1.doubanio.com/img/celebrity/large/118.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/118.jpg"},"name":"艾伦·佩吉","id":"1012520"}],"collect_count":881867,"original_title":"Inception","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1054524/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/673.jpg","large":"https://img3.doubanio.com/img/celebrity/large/673.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/673.jpg"},"name":"克里斯托弗·诺兰","id":"1054524"}],"year":"2010","images":{"small":"https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p513344864.jpg","large":"https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p513344864.jpg","medium":"https://img3.doubanio.com/view/movie_poster_cover/spst/public/p513344864.jpg"},"alt":"https://movie.douban.com/subject/3541415/","id":"3541415"},{"rating":{"max":10,"average":9.2,"stars":"45","min":0},"genres":["剧情","音乐"],"title":"海上钢琴师","casts":[{"alt":"https://movie.douban.com/celebrity/1025176/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/6281.jpg","large":"https://img3.doubanio.com/img/celebrity/large/6281.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/6281.jpg"},"name":"蒂姆·罗斯","id":"1025176"},{"alt":"https://movie.douban.com/celebrity/1010659/","avatars":{"small":"https://img5.doubanio.com/img/celebrity/small/1355152571.6.jpg","large":"https://img5.doubanio.com/img/celebrity/large/1355152571.6.jpg","medium":"https://img5.doubanio.com/img/celebrity/medium/1355152571.6.jpg"},"name":"普路特·泰勒·文斯","id":"1010659"},{"alt":"https://movie.douban.com/celebrity/1027407/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/12333.jpg","large":"https://img3.doubanio.com/img/celebrity/large/12333.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/12333.jpg"},"name":"比尔·努恩","id":"1027407"}],"collect_count":726174,"original_title":"La leggenda del pianista sull'oceano","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1018983/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/195.jpg","large":"https://img3.doubanio.com/img/celebrity/large/195.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/195.jpg"},"name":"朱塞佩·托纳多雷","id":"1018983"}],"year":"1998","images":{"small":"https://img1.doubanio.com/view/movie_poster_cover/ipst/public/p511146957.jpg","large":"https://img1.doubanio.com/view/movie_poster_cover/lpst/public/p511146957.jpg","medium":"https://img1.doubanio.com/view/movie_poster_cover/spst/public/p511146957.jpg"},"alt":"https://movie.douban.com/subject/1292001/","id":"1292001"}]
     * title : 豆瓣电影Top250
     */

    @SerializedName("count")
    private int count;
    @SerializedName("start")
    private int start;
    @SerializedName("total")
    private int total;
    @SerializedName("title")
    private String title;
    @SerializedName("subjects")
    private List<SubjectsBean> subjects;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SubjectsBean> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectsBean> subjects) {
        this.subjects = subjects;
    }

    public static class SubjectsBean {
        /**
         * rating : {"max":10,"average":9.6,"stars":"50","min":0}
         * genres : ["犯罪","剧情"]
         * title : 肖申克的救赎
         * casts : [{"alt":"https://movie.douban.com/celebrity/1054521/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/17525.jpg","large":"https://img3.doubanio.com/img/celebrity/large/17525.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/17525.jpg"},"name":"蒂姆·罗宾斯","id":"1054521"},{"alt":"https://movie.douban.com/celebrity/1054534/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/34642.jpg","large":"https://img3.doubanio.com/img/celebrity/large/34642.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/34642.jpg"},"name":"摩根·弗里曼","id":"1054534"},{"alt":"https://movie.douban.com/celebrity/1041179/","avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/5837.jpg","large":"https://img1.doubanio.com/img/celebrity/large/5837.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/5837.jpg"},"name":"鲍勃·冈顿","id":"1041179"}]
         * collect_count : 1003335
         * original_title : The Shawshank Redemption
         * subtype : movie
         * directors : [{"alt":"https://movie.douban.com/celebrity/1047973/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/230.jpg","large":"https://img3.doubanio.com/img/celebrity/large/230.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/230.jpg"},"name":"弗兰克·德拉邦特","id":"1047973"}]
         * year : 1994
         * images : {"small":"https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p480747492.jpg","large":"https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p480747492.jpg","medium":"https://img3.doubanio.com/view/movie_poster_cover/spst/public/p480747492.jpg"}
         * alt : https://movie.douban.com/subject/1292052/
         * id : 1292052
         */

        @SerializedName("rating")
        private RatingBean rating;
        @SerializedName("title")
        private String title;
        @SerializedName("collect_count")
        private int collectCount;
        @SerializedName("original_title")
        private String originalTitle;
        @SerializedName("subtype")
        private String subtype;
        @SerializedName("year")
        private String year;
        @SerializedName("images")
        private ImagesBean images;
        @SerializedName("alt")
        private String alt;
        @SerializedName("id")
        private String id;
        @SerializedName("genres")
        private List<String> genres;
        @SerializedName("casts")
        private List<CastsBean> casts;
        @SerializedName("directors")
        private List<DirectorsBean> directors;

        public RatingBean getRating() {
            return rating;
        }

        public void setRating(RatingBean rating) {
            this.rating = rating;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getCollectCount() {
            return collectCount;
        }

        public void setCollectCount(int collectCount) {
            this.collectCount = collectCount;
        }

        public String getOriginalTitle() {
            return originalTitle;
        }

        public void setOriginalTitle(String originalTitle) {
            this.originalTitle = originalTitle;
        }

        public String getSubtype() {
            return subtype;
        }

        public void setSubtype(String subtype) {
            this.subtype = subtype;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public ImagesBean getImages() {
            return images;
        }

        public void setImages(ImagesBean images) {
            this.images = images;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<String> getGenres() {
            return genres;
        }

        public void setGenres(List<String> genres) {
            this.genres = genres;
        }

        public List<CastsBean> getCasts() {
            return casts;
        }

        public void setCasts(List<CastsBean> casts) {
            this.casts = casts;
        }

        public List<DirectorsBean> getDirectors() {
            return directors;
        }

        public void setDirectors(List<DirectorsBean> directors) {
            this.directors = directors;
        }

        public static class RatingBean {
            /**
             * max : 10
             * average : 9.6
             * stars : 50
             * min : 0
             */

            @SerializedName("max")
            private int max;
            @SerializedName("average")
            private double average;
            @SerializedName("stars")
            private String stars;
            @SerializedName("min")
            private int min;

            public int getMax() {
                return max;
            }

            public void setMax(int max) {
                this.max = max;
            }

            public double getAverage() {
                return average;
            }

            public void setAverage(double average) {
                this.average = average;
            }

            public String getStars() {
                return stars;
            }

            public void setStars(String stars) {
                this.stars = stars;
            }

            public int getMin() {
                return min;
            }

            public void setMin(int min) {
                this.min = min;
            }
        }

        public static class ImagesBean {
            /**
             * small : https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p480747492.jpg
             * large : https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p480747492.jpg
             * medium : https://img3.doubanio.com/view/movie_poster_cover/spst/public/p480747492.jpg
             */

            @SerializedName("small")
            private String small;
            @SerializedName("large")
            private String large;
            @SerializedName("medium")
            private String medium;

            public String getSmall() {
                return small;
            }

            public void setSmall(String small) {
                this.small = small;
            }

            public String getLarge() {
                return large;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public String getMedium() {
                return medium;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }
        }

        public static class CastsBean {
            /**
             * alt : https://movie.douban.com/celebrity/1054521/
             * avatars : {"small":"https://img3.doubanio.com/img/celebrity/small/17525.jpg","large":"https://img3.doubanio.com/img/celebrity/large/17525.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/17525.jpg"}
             * name : 蒂姆·罗宾斯
             * id : 1054521
             */

            @SerializedName("alt")
            private String alt;
            @SerializedName("avatars")
            private AvatarsBean avatars;
            @SerializedName("name")
            private String name;
            @SerializedName("id")
            private String id;

            public String getAlt() {
                return alt;
            }

            public void setAlt(String alt) {
                this.alt = alt;
            }

            public AvatarsBean getAvatars() {
                return avatars;
            }

            public void setAvatars(AvatarsBean avatars) {
                this.avatars = avatars;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public static class AvatarsBean {
                /**
                 * small : https://img3.doubanio.com/img/celebrity/small/17525.jpg
                 * large : https://img3.doubanio.com/img/celebrity/large/17525.jpg
                 * medium : https://img3.doubanio.com/img/celebrity/medium/17525.jpg
                 */

                @SerializedName("small")
                private String small;
                @SerializedName("large")
                private String large;
                @SerializedName("medium")
                private String medium;

                public String getSmall() {
                    return small;
                }

                public void setSmall(String small) {
                    this.small = small;
                }

                public String getLarge() {
                    return large;
                }

                public void setLarge(String large) {
                    this.large = large;
                }

                public String getMedium() {
                    return medium;
                }

                public void setMedium(String medium) {
                    this.medium = medium;
                }
            }
        }

        public static class DirectorsBean {
            /**
             * alt : https://movie.douban.com/celebrity/1047973/
             * avatars : {"small":"https://img3.doubanio.com/img/celebrity/small/230.jpg","large":"https://img3.doubanio.com/img/celebrity/large/230.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/230.jpg"}
             * name : 弗兰克·德拉邦特
             * id : 1047973
             */

            @SerializedName("alt")
            private String alt;
            @SerializedName("avatars")
            private AvatarsBeanX avatars;
            @SerializedName("name")
            private String name;
            @SerializedName("id")
            private String id;

            public String getAlt() {
                return alt;
            }

            public void setAlt(String alt) {
                this.alt = alt;
            }

            public AvatarsBeanX getAvatars() {
                return avatars;
            }

            public void setAvatars(AvatarsBeanX avatars) {
                this.avatars = avatars;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public static class AvatarsBeanX {
                /**
                 * small : https://img3.doubanio.com/img/celebrity/small/230.jpg
                 * large : https://img3.doubanio.com/img/celebrity/large/230.jpg
                 * medium : https://img3.doubanio.com/img/celebrity/medium/230.jpg
                 */

                @SerializedName("small")
                private String small;
                @SerializedName("large")
                private String large;
                @SerializedName("medium")
                private String medium;

                public String getSmall() {
                    return small;
                }

                public void setSmall(String small) {
                    this.small = small;
                }

                public String getLarge() {
                    return large;
                }

                public void setLarge(String large) {
                    this.large = large;
                }

                public String getMedium() {
                    return medium;
                }

                public void setMedium(String medium) {
                    this.medium = medium;
                }
            }
        }
    }
}
