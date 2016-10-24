package com.lingjoin.redupRemover;

public class RedupRemover {

	public static boolean state=false;
	
	public static boolean init(String arg){
		state=CRedupRemoverLibrary.instance.RR_Init("", arg, false, "0", 1);
		if(state){
			return state;
		}else{
			System.out.println(CRedupRemoverLibrary.instance.RR_GetLastErrMsg());
			return false;
		}
	}
	
	public int fileProcess(String fileContent, String sID){
		if(state){
			return CRedupRemoverLibrary.instance.RR_FileProcess(fileContent, sID);
		}else{
			System.out.println(CRedupRemoverLibrary.instance.RR_GetLastErrMsg());
			return 0;
		}
	}
	
	public boolean findRepeat(byte[] returnString, boolean bAllOutput){
		if(state){
			return CRedupRemoverLibrary.instance.RR_FindRepeat(returnString, bAllOutput);
		}else{
			System.out.println(CRedupRemoverLibrary.instance.RR_GetLastErrMsg());
			return false;
		}
	}
	
	public boolean output(String sHistoryDataFile){
		if(state){
			return CRedupRemoverLibrary.instance.RR_Output(sHistoryDataFile);
		}else{
			System.out.println(CRedupRemoverLibrary.instance.RR_GetLastErrMsg());
			return false;
		}
	}
	
	public boolean saveHistoryData(String DataFilePath){
		if(state){
			return CRedupRemoverLibrary.instance.RR_SaveHistoryData(DataFilePath);
		}else{
			System.out.println(CRedupRemoverLibrary.instance.RR_GetLastErrMsg());
			return false;
		}
	}
	
	public float getDistance(String str1,String str2){
		if(!state){
			System.out.println(CRedupRemoverLibrary.instance.RR_GetLastErrMsg());
			return 0;
		}else{
			float distance;
			int w1L=str1.length();
			int w2L=str2.length();
			int[] w1=new int[w1L+1];
			int[] w2=new int[w2L+1];
			int maxLength=Math.max(str1.length(), str2.length());
			int[][] disMatrix=new int[w2L+1][w1L+1];
			w1[0]=0;
			w2[0]=0;
			for(int w=1;w<w1L+1;w++){
				w1[w]=(int)str1.charAt(w-1);
			}
			for(int w=1;w<w2L+1;w++){
				w2[w]=(int)str2.charAt(w-1);
			}
			for(int i=0;i<w1L+1;i++){
				disMatrix[0][i]=i;
			}
			for(int j=0;j<w2L+1;j++){
				disMatrix[j][0]=j;
			}
			int dis;
			for(int i=1;i<w1L+1;i++){
				for(int j=1;j<w2L+1;j++){
					if(w1[i]==w2[j]) dis=disMatrix[j-1][i-1]+0;
					else dis=disMatrix[j-1][i-1]+1;
					disMatrix[j][i]=Math.min(Math.min(disMatrix[j-1][i]+1, disMatrix[j][i-1]+1), dis);
				}
			}
			distance=1-(float)disMatrix[w2L][w1L]/maxLength;
			return distance;
		}
	}
	
	public void RR_Exit(){
		CRedupRemoverLibrary.instance.RR_Exit();
	}
}
