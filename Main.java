import java.util.ArrayList;
import java.util.Scanner;

class Main {
    public static void main(String  args[]){
        String filename = "BaiTap.txt";
        String fileResultCount = "resultCount.txt";
        String fileResultSort = "resultAfterSort.txt";
        FileProcessing fileProcess = new FileProcessing();
        String regex = fileProcess.regexOfWord();
        //lấy nội dung của file
        String content = fileProcess.readFile(filename);
        //đếm số từ 
        int totalWord = fileProcess.countWord(content,regex);
        System.out.println("tong so tu la: "+ totalWord);
        //đếm số lần lặp lại của từ, sau đó ghi vào file 
        //
        ArrayList<String> arrWord = fileProcess.getWordFromString(content, regex);
        fileProcess.countDuplicate(arrWord,fileResultCount);
        // Sắp xếp các từ dựa vào số lần xuất hiện, nhiều -> ít 
        ArrayList<Element> listSorted= fileProcess.sortByCount(arrWord, fileResultSort);
        // tìm kiếm theo từ 
        Scanner scanner = new Scanner(System.in);
        System.out.print("nhap tu can tim kiem: ");
        String word = scanner.nextLine();
        fileProcess.searchWord(word, listSorted, 5);
        scanner.close();

    }
}