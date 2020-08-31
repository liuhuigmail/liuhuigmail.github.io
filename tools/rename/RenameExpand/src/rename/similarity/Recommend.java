package rename.similarity;

import java.util.List;
import java.util.Map;

import rename.model.DataModel;


public class Recommend {
	
	public static String detect(DataModel renameData, List<DataModel> ignoreRenameDataList, String candidateName){
		String suggest = "";		
		suggest = detect(renameData, candidateName);					
		return suggest;				
	}
	
	
	public static String detect(DataModel renameData, String candidateName){
		
		String originalName = renameData.originalName;
		String subsequentName = renameData.subsequentName;				
		return detect(originalName, subsequentName, candidateName);			
	}
	
	
	public static String detect(String originalName, String subsequentName, String candidateName){
		
		String[] originalNames = Word.splitWords(originalName);
		String[] originalNamesdiff = Word.splitWordsToLowerCase(originalName);
		String[] subsequentNames = Word.splitWords(subsequentName);
		String[] subsequentNamesDiff = Word.splitWordsToLowerCase(subsequentName);
		String[] candidateNames = Word.splitWords(candidateName);
		
		
		
		List diffOut = Similarity.getDiff(originalNamesdiff, subsequentNamesDiff);
		if(diffOut == null || diffOut.size() == 0) return null;
		
		String[] splitOriginalNames = Similarity.splitOriginalName(diffOut, originalNames) ;
		String[] splitCandidateNames = Similarity.splitCandidateName(diffOut, originalNames, candidateNames);
		if(splitOriginalNames == null || splitCandidateNames == null)return null;
		if(splitOriginalNames.length == 0 || splitCandidateNames.length == 0)return null;
		int length = diffOut.size();
		String suggest = "";
		int startPos = 0;
		for(int i = 0; i < length; i++){
			Difference diff = (Difference)diffOut.get(i);
			String str = splitCandidateNames[i];
			if(diff.getAddedEnd() == -1){
				String[] strs =  Word.splitWords(str);
				int len = diff.getDeletedEnd()-diff.getDeletedStart()+1;
				if(strs.length > len){
					for(int j = 0; j < strs.length - len; j++){
						suggest = suggest + strs[j];
					}
				}
			} else if(diff.getDeletedEnd() == -1){
				suggest = suggest + str;
				for(int j = diff.getAddedStart(); j <= diff.getAddedEnd(); j++){
					suggest = suggest + subsequentNames[j];
				}				
			} else {
				String[] strs =  Word.splitWords(str);
				int len = diff.getDeletedEnd()-diff.getDeletedStart()+1;
				if(strs.length > len){
					for(int j = 0; j < strs.length - len; j++){
						suggest = suggest + strs[j];
					}
				}				
				for(int j = diff.getAddedStart(); j <= diff.getAddedEnd(); j++){
					suggest = suggest + subsequentNames[j];
				}				
			}			
		}
		
		suggest = suggest + splitCandidateNames[length];
		if(suggest != ""){
			suggest = suggest.subSequence(0, 1).toString().toLowerCase() + suggest.substring(1);
		}
		return suggest;
	}
	
	
//	public static String detect(String originalName, String subsequentName, String candidateName){
//		Map<String, String> originalMap = Word.splitAXB(originalName, subsequentName);
//		String x = originalMap.get("X");
//		String y = originalMap.get("Y");
//		
//		Map<String, String> candidateMap = Word.splitCXD(originalName, subsequentName, candidateName);
//		String tempC = candidateMap.get("C");
//		String tempD = candidateMap.get("D");	
//		
//		String c = "";
//		String d = "";
//		String suggest = "";
//		if(x.equals("")){
//			if(tempC.equals("")) return y + candidateName;
//			if(tempD.equals("")) return candidateName + y;
//			String[] tempDs = Word.splitWords(tempD);
//			c = candidateName.substring(0, candidateName.indexOf(tempDs[0]));
//			d = candidateName.substring(candidateName.indexOf(tempDs[0]));			
//		} else {
//			if(candidateName.indexOf(x) > 0) c = candidateName.substring(0, candidateName.indexOf(x));
//			
//			String reservedCandidateName = Word.reserved(candidateName);
//			String reservedX = Word.reserved(x);
//			if(reservedCandidateName.indexOf(reservedX) > 0) {
//				String reservedD = reservedCandidateName.substring(0, reservedCandidateName.indexOf(reservedX));
//				d = Word.reserved(reservedD);
//			}		
//		}		
//		suggest = c + y + d;				
//		return suggest;				
//	}
}
