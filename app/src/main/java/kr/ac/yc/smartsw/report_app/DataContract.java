package kr.ac.yc.smartsw.report_app;

public final class DataContract {
    private DataContract(){}

    public static class mainEntry{//mainData.db처리용
        public static final String TABLE_NAME = "mainData";//사용할 테이블 이름 지정
        public static final String COLUMN_DATANUM = "dataNum";
        public static final String COLUMN_USER = "user";//여기에 칼럼을 지정한다.
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_MONEY = "money";
        public static final String COLUMN_KIND = "kind";
        public static final String COLUMN_DATE = "date";
        public static final String SQL_CREATE_TABLE =//테이블을 Create 할때 쓰는 문장이다.
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                        COLUMN_DATANUM+ " INTEGER primary key autoincrement ," +
                        COLUMN_USER + " TEXT," +
                        COLUMN_DESCRIPTION + " TEXT," +
                        COLUMN_MONEY+" INTEGER," +
                        COLUMN_KIND+" INTEGER," +
                        COLUMN_DATE+" TEXT)";//date가 없다고 계속 나오네..<-띄어쓰기 안했죠?
        public static final String SQL_DELETE_TABLE =//테이블을 DELETE하기위한 문장이다.
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static class updateEntry{//updateData.db처리용
        public static final String TABLE_NAME = "updateData";
        public static final String COLUMN_DATANUM = "dataNum";
        public static final String COLUMN_USER = "user";//여기에 칼럼을 지정한다.
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_DATE = "date";
        public static final String SQL_CREATE_TABLE =//테이블을 Create 할때 쓰는 문장이다.
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                        COLUMN_DATANUM+ " INTEGER primary key autoincrement," +
                        COLUMN_USER + " TEXT," +
                        COLUMN_DESCRIPTION + " TEXT," +
                        COLUMN_DATE+" TEXT)";
        public static final String SQL_DELETE_TABLE =//테이블을 DELETE하기위한 문장이다.
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
