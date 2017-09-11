package com.mcxtzhang.binderdemo.aidl;

import com.mcxtzhang.binderdemo.aidl.Book;

interface IBookManager{
List<Book> getBookList();
void addBook(in Book book);

}