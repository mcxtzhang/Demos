// OnBookAddObserver.aidl
package com.mcxtzhang.aidldemoserver;
import com.mcxtzhang.aidldemoserver.Book;

// Declare any non-default types here with import statements

interface OnBookAddObserver {
    void onBookAdded(in Book newBook);
}
