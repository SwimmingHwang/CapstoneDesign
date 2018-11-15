package kr.sj.obapm;

public class DBContract {
    public static final String TBL_USER ="USER_T ";
    public static final String COL_MID = "MEMBER_ID " ;
    public static final String COL_MPWD = "MEMBER_PWD " ;
    public static final String COL_NAME = "USER_NAME " ;
    public static final String COL_GEN = " USER_GEN ";
    public static final String COL_AGE = "USER_AGE ";
    public static final String COL_H   = " USER_HEIGHT ";
    public static final String COL_W   = " USER_WEIGHT ";

    public static final String TBL_USER_MEAL ="USER_MEAL_T ";
    public static final String COL_DATE ="DATE ";
    public static final String COL_BREAKFAST = "MEAL_BREAKFAST ";
    public static final String COL_LUNCH = "MEAL_LUNCH ";
    public static final String COL_DINNER = "MEAL_DINNER ";
    public static final String COL_ETC = "MEAL_ETC";

    public static final String TBL_USER_NUTRIENT ="USER_NUTRIENT_T ";
    public static final String COL_CAL ="USER_CALORIE ";
    public static final String COL_CARBO = "USER_CARBOHVDRATE ";
    public static final String COL_PROTEIN = "USER_PROTEIN ";
    public static final String COL_FAT = "USER_FAT ";
    public static final String COL_VITAMIN = "USER_VITAMIN ";
    public static final String COL_DIETARY = "USER_DIETARY ";
    public static final String COL_SODIUM = "USER_SODIUM ";
    public static final String COL_CALCIUM = "USER_CALCIUM ";

    public static final String SQL_CREATE_USER_TBL = "CREATE TABLE IF NOT EXISTS USER_T (" +
            COL_MID         + "TEXT," +
            COL_MPWD        + "TEXT," +
            COL_NAME        + "TEXT," +
            COL_GEN         + "TEXT," +
            COL_AGE         + "INTEGER," +
            COL_H           + "REAL," +
            COL_W           + "REAL" +
            ")" ;

    public static final String SQL_CREATE_USER_MEAL_TBL = "CREATE TABLE IF NOT EXISTS USER_MEAL_T (" +
            COL_DATE         + "DATE," +
            COL_BREAKFAST    + "INTEGER," +
            COL_LUNCH        + "INTEGER," +
            COL_DINNER       + "INTEGER," +
            COL_ETC          + "INTEGER" +
            ")" ;

    public static final String SQL_CREATE_USER_NUTRIENT_TBL = "CREATE TABLE IF NOT EXISTS USER_NUTRIENT_T (" +
            COL_CAL         + "REAL," +
            COL_CARBO       + "REAL," +
            COL_PROTEIN     + "REAL," +
            COL_FAT         + "REAL," +
            COL_VITAMIN     + "REAL," +
            COL_DIETARY     + "REAL," +
            COL_SODIUM      + "REAL," +
            COL_CALCIUM     + "REAL" +
            ")" ;


    public static final String SQL_DROP_USER_TBL = "DROP TABLE IF EXISTS " + TBL_USER ;
    public static final String SQL_DROP_USER_MEAL_TBL = "DROP TABLE IF EXISTS " + TBL_USER_MEAL ;
    public static final String SQL_DROP_USER_NUTRIENT_TBL = "DROP TABLE IF EXISTS " + TBL_USER_NUTRIENT ;

    public static final String SQL_SELECT_USER = "SELECT * FROM " + TBL_USER ;
    public static final String SQL_SELECT_USER_MEAL = "SELECT * FROM " + TBL_USER_MEAL ;
    public static final String SQL_SELECT_USER_NUTRIENT = "SELECT * FROM " + TBL_USER_NUTRIENT ;

    public static final String SQL_DELETE = "DELETE FROM " + TBL_USER ;


}
