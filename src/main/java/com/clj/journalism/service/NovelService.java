package com.clj.journalism.service;

import com.clj.journalism.bean.Book;
import com.clj.journalism.bean.Novel;
import com.clj.journalism.mapper.BookMapper;
import com.clj.journalism.mapper.NovelMapper;
import com.clj.journalism.util.NovelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NovelService {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private NovelMapper novelMapper;

    public String addNovel(String url,Integer cId) throws Exception{
        Book book = NovelUtil.bookpc(url);
        List<Book> bookName = bookMapper.getBookName(book.getName());
        if(bookName.isEmpty()){
            book.setcId(cId);
            bookMapper.insert(book);
            List<Novel> novel = NovelUtil.novelpc(url);
            List<Novel> novelList = new ArrayList<Novel>();
            Novel n = null;
            for(Novel novel1:novel){
                n=new Novel();
                n.setContent(novel1.getContent());
                n.setbId(book.getbId());
                n.setTitle(novel1.getTitle());
                novelList.add(n);
            }

            novelMapper.insert(novelList);
            System.out.println(book.getbId());
            return "success";
        }else{
            return book.getName()+"已存在";
        }


    }
    public List<Book> getNovelAll(){
        return bookMapper.getNovelAll();
    }

    public String pcNovelAll(String url,Integer cId) throws Exception{
        List<String> urls = NovelUtil.getUrl(url);
        for(String str : urls){
            System.out.println("url="+str);
            Book book =  NovelUtil.bookpc(str);
        if(book.getName()!=null||book.getName().equals("")) {
            List<Book> bookName = bookMapper.getBookName(book.getName());
            if (bookName.isEmpty()) {
                book.setcId(cId);
                bookMapper.insert(book);
                List<Novel> list = NovelUtil.novelpc(str);
                List<Novel> novelList = new ArrayList<Novel>();
                Novel n = null;
                for (Novel novel1 : list) {
                    n = new Novel();
                    n.setContent(novel1.getContent());
                    n.setbId(book.getbId());
                    n.setTitle(novel1.getTitle());
                    novelList.add(n);
                }
                novelMapper.insert(novelList);
                System.out.println(book.getbId());
            } else {
                System.out.println(book.getName() + "已存在");
            }
        }else {
            System.out.println("错误的链接-跳过");
        }
        }
        return "success";
    }
}
