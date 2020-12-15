import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileProcessing {
    // Đọc từ file text ra
    // Đếm tổng số từ có trong file
    // In ra tần xuất số lần xuất hiện của các từ
    // Sắp xếp các từ dựa vào số lần xuất hiện, từ nào xuất hiện nhiều nhất xếp lên
    // trên còn lại nhỏ nhất xếp phía sau
    // Tìm kiếm các từ với chữ cái đầu tiên dựa vào xuất hiện của các từ
    // Viết kết quả ra file mới
   
    public static String readFile(String filename) {
        //khởi tạo 
        BufferedReader buffer = null;
        StringBuilder result = new StringBuilder();
        String s = "";
        try {
            //lấy buffer từ file 
            buffer = new BufferedReader(new FileReader(filename));
            //đọc theo dòng 
            while ((s = buffer.readLine()) != null) {
                //nối chuỗi 
                result.append(s);
            }
            buffer.close();
            return result.toString();
        }catch(FileNotFoundException e){
            return e.toString();
        }catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        } 
    }
    public static ArrayList<String> getWordFromString(String content, String regex){
        //tạo mảng chứa dữ liệu tách được 
        ArrayList<String> allMatches = new ArrayList<String>();
        //tạo pattern 
        Matcher m = Pattern.compile(regex)
                .matcher(content);
        //tìm kiếm theo pattern 
        while (m.find()) {
        allMatches.add(m.group());
        }
        return allMatches;
    }
    public static int countWord (String s, String regex){
        //trả về số lượng của mảng 
        return getWordFromString(s,regex).size();
    }
    public static String regexOfWord(){
        return "([a-zA-Z]+([-']?[a-zA-Z]+)?)|(-?\\d+([.,]\\d+)?%?)";
        // có thể khớp với các từ 
        // hello team-building i'm 1.2 -1,2 -1.4 3%  
    }
    public static HashMap<String,Integer> createMap(ArrayList<String> arr){
        //khai báo hash map 
        HashMap < String, Integer > map = new HashMap < > ();
        int i;
        String word;
        //duyệt mảng 
        for (i=0;i< arr.size();i++) {
            word = arr.get(i);
            if (map.containsKey(word)){
            int count = map.get(word);//lấy số lần lặp 
            map.put(word, count + 1);// ghi biến đếm 
            } else {
            //nếu không trùng thì đếm bằng 1 
            map.put(word, 1); 
            }
        }
        return map;
    }
    public static void countDuplicate(ArrayList<String> arr,String filename){
        writeHashMapToFile(createMap(arr), filename);
    }
    public static void writeHashMapToFile(HashMap<String,Integer> listCount, String filename){
        //khởi tạo với buffer 
        File file = new File(filename);
        BufferedWriter buffWrite = null;;
        try{
            buffWrite = new BufferedWriter( new FileWriter(file) );
            //tạo keyset để duyệt 
            Set<String> keySet = listCount.keySet();
            for(String key : keySet){
                //ghi vào buffer 
                buffWrite.write(key+" :"+ listCount.get(key)+"\n");
            } 
            buffWrite.flush();
        }catch(IOException e){
            e.printStackTrace();
        }finally{ 
            try{
                buffWrite.close();
            }catch(Exception e){}
        }
    }
    
    public static void writeArrayListToFile(ArrayList<Element >element, String filename){
        //khởi tạo với buffer 
        File file = new File(filename);
        BufferedWriter buffWrite = null;
        Element ele;
        try{
            buffWrite = new BufferedWriter( new FileWriter(file) );
            //tạo keyset để duyệt 
            int i;
            for(i=0;i< element.size();i++){
                //ghi vào buffer 
                ele = element.get(i);
                buffWrite.write(ele.getName()+" :"+ ele.getCount()+"\n");
            } 
            buffWrite.flush();
        }catch(IOException e){
            e.printStackTrace();
        }finally{ 
            try{
                buffWrite.close();
            }catch(Exception e){}
        }
    }
   
    public static ArrayList<Element> sortByCount(ArrayList<String> arr,String filename){
        //khởi tạo 1 list 
        ArrayList<Element> listItem = convertHashmapToArrayList(createMap(arr));
        //tạo cách so sánh 
        Comparator<Element> comp = new Comparator<Element>() {
            @Override
            public int compare(Element e1, Element e2) {
                return e2.getCount().compareTo(e1.getCount());
            }
        };
        //sắp xếp 
        Collections.sort(listItem,comp);
        //ghi vào file 
        writeArrayListToFile(listItem, filename);  
        return listItem;
    }
    public static ArrayList<Element> convertHashmapToArrayList(HashMap<String,Integer> listCount){
        Set<String> keySet = listCount.keySet();
        ArrayList<Element> arrItem = new ArrayList<>();
        //duyệt mảng 
            for(String key : keySet){
                Element item = new Element();
                //set giá trị 
                item.setValue(key, listCount.get(key));
                arrItem.add(item);
            } 
        return arrItem;
    }
    public static void searchWord(String word,ArrayList<Element> listSorted, int number){
        int i;
        int count = 0;
        String w;
        //duyệt mảng 
        for(i=0;i< listSorted.size();i++){
            w = listSorted.get(i).getName();
            //kiểm tra điều kiện 
            if(w.startsWith(word)){
                System.out.println(w);
                count++;
            }
            if(count == number) break;
        }
        if(count ==0){
            System.out.println("khong co tu phu hop");
        }
    }
    
}
