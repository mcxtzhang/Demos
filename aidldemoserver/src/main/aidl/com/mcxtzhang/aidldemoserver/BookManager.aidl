// BookManager.aidl
package com.mcxtzhang.aidldemoserver;
import com.mcxtzhang.aidldemoserver.Book;

// Declare any non-default types here with import statements

interface BookManager {
 /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    List<Book> getBooks();
    Book getBook(String bookName);
    int getBookCount();

    //传参时除了Java基本类型以及String，CharSequence之外的类型
    //都需要在前面加上定向tag，具体加什么量需而定
    void addBook(in Book book);
}
