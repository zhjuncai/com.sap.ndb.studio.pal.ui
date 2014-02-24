package com.sap.ndb.studio.pal.ui.sql;

import java.util.ArrayList;


public class ClusteringAlg {
	public static void AffinityPropagation(ArrayList<String> sqlString,String schema,String tableName,String[] parameter,String[] columnName,String[] columnType){
		sqlString.clear();
		sqlString.add("set SCHEMA " + schema);
		sqlString.add("DROP TYPE PAL_AP_DATA_T");
		String tempSql = null;
		tempSql = "CREATE TYPE PAL_AP_DATA_T AS TABLE (" +"\""+"ID"+"\""+" INTEGER";
		for(int i=0;i<columnName.length;i++){
			tempSql += ",  "+"\""+columnName[i]+"\"  "+columnType[i];
		}
		tempSql += ")";
		sqlString.add(tempSql);
		sqlString.add("DROP TYPE PAL_AP_SEED_T");
		sqlString.add("CREATE TYPE PAL_AP_SEED_T AS TABLE (" 
					+"\""+ "ID"+"\""+" INTEGER, "
					+"\""+"SEED" +"\""+ " INTEGER)");
		sqlString.add("DROP TYPE PAL_CONTROL_T");
		sqlString.add("CREATE TYPE PAL_CONTROL_T AS TABLE(" 
					+ "\"" + "NAME" + "\"" + "VARCHAR(50), " 
					+ "\"" + "INTARGS" + "\"" + "INTEGER, " 
					+ "\"" + "DOUBLEARGS" + "\"" + "DOUBLE, " 
					+ "\"" + "STRINGARGS" + "\"" + "VARCHAR(100))");
		sqlString.add("DROP TYPE PAL_AP_RESULTS_T;");
		sqlString.add("CREATE TYPE PAL_AP_RESULTS_T AS TABLE(" 
					+ "\"" + "ID" + "\"" + "INTEGER, "
					+ "\"" + "RESULT" + "\"" + "INTEGER)");
		sqlString.add("DROP TABLE PAL_AP_PDATA_TBL");
		sqlString.add("CREATE COLUMN TABLE PAL_AP_PDATA_TBL ("
					+ "\"" + "ID" + "\"" + "INTEGER, "
					+ "\"" + "TYPENAME" + "\"" + "VARCHAR(100), "
					+ "\"" + "DIRECTION" + "\"" + "VARCHAR(100) )");
		sqlString.add("INSERT INTO PAL_AP_PDATA_TBL VALUES (1, 'DM_PAL.PAL_AP_DATA_T', 'in')");
		sqlString.add("INSERT INTO PAL_AP_PDATA_TBL VALUES (2, 'DM_PAL.PAL_AP_SEED_T', 'in')");
		sqlString.add("INSERT INTO PAL_AP_PDATA_TBL VALUES (3, 'DM_PAL.PAL_CONTROL_T', 'in')");
		sqlString.add("INSERT INTO PAL_AP_PDATA_TBL VALUES (4, 'DM_PAL.PAL_AP_RESULTS_T', 'out')");
		sqlString.add("CALL SYSTEM.afl_wrapper_eraser('PAL_AP')");
		sqlString.add("CALL SYSTEM.afl_wrapper_generator('PAL_AP', 'AFLPAL', 'AP', DM_PAL.PAL_AP_PDATA_TBL)");
		sqlString.add("DROP TABLE PAL_AP_DATA_TBL");
		sqlString.add("CREATE COLUMN TABLE PAL_AP_DATA_TBL LIKE PAL_AP_DATA_T");
		tempSql = "SELECT ID ";
		for(int i=0;i<columnName.length;i++){
			tempSql += ", "+columnName[i];
		}
		tempSql += " from " + tableName + " INTO PAL_AP_DATA_TBL";
		sqlString.add(tempSql);
		sqlString.add("DROP TABLE PAL_AP_SEED_TBL");
		sqlString.add("CREATE COLUMN TABLE PAL_AP_SEED_TBL LIKE PAL_AP_SEED_T");
		sqlString.add("CREATE LOCAL TEMPORARY COLUMN TABLE #PAL_CONTROL_TBL LIKE PAL_CONTROL_T");
		sqlString.add("INSERT INTO #PAL_CONTROL_TBL VALUES('THREAD_NUMBER',"+parameter[0]+",null,null)");
		sqlString.add("INSERT INTO #PAL_CONTROL_TBL VALUES('MAX_ITERATION',"+parameter[1]+",null,null)");
		sqlString.add("INSERT INTO #PAL_CONTROL_TBL VALUES('CON_ITERATION',"+parameter[2]+",null,null)");
		sqlString.add("INSERT INTO #PAL_CONTROL_TBL VALUES('DAMP',null,"+parameter[3]+",null)");
		sqlString.add("INSERT INTO #PAL_CONTROL_TBL VALUES('PREFERENCE',null,"+parameter[4]+",null)");
		sqlString.add("INSERT INTO #PAL_CONTROL_TBL VALUES('SEED_RATIO',null,"+parameter[5]+",null)");
		sqlString.add("INSERT INTO #PAL_CONTROL_TBL VALUES('TIMES',"+parameter[6]+",null,null)");
		sqlString.add("INSERT INTO #PAL_CONTROL_TBL VALUES('DISTANCE_METHOD',"+parameter[7]+",null,null)");
		sqlString.add("INSERT INTO #PAL_CONTROL_TBL VALUES('MINKOW_P',"+parameter[8]+",null,null)");
		sqlString.add("INSERT INTO #PAL_CONTROL_TBL VALUES('CLUSTER_NUMBER',"+parameter[9]+",null,null)");
		sqlString.add("DROP TABLE PAL_AP_RESULTS_TBL");
		sqlString.add("CREATE COLUMN TABLE PAL_AP_RESULTS_TBL LIKE PAL_AP_RESULTS_T");
		sqlString.add("CALL _SYS_AFL.PAL_AP(PAL_AP_DATA_TBL, PAL_AP_SEED_TBL, #PAL_CONTROL_TBL, PAL_AP_RESULTS_TBL) with OVERVIEW");
		sqlString.add("commit");
	}
}
