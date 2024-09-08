package com.group.libraryapp.service.book;

import com.group.libraryapp.domain.book.Book;
import com.group.libraryapp.domain.book.BookRepository;
import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository;
import com.group.libraryapp.dto.book.request.BookCreateRequest;
import com.group.libraryapp.dto.book.request.BookLoanRequest;
import com.group.libraryapp.dto.book.request.BookReturnRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final UserLoanHistoryRepository userloanHistoryRepository;
    private final UserRepository userRepository;

    public BookService(BookRepository bookRepository, UserLoanHistoryRepository userloanHistoryRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userloanHistoryRepository = userloanHistoryRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveBook(BookCreateRequest request) {
        bookRepository.save(new Book(request.getName()));
    }

    @Transactional
    public void loanBook(BookLoanRequest request) {
        // 필요한 기능 : 대출 중인지 아닌지 확인
        // 1. 책 정보 가져오기
        Book book = bookRepository.findByName(request.getBookName())
                .orElseThrow(IllegalArgumentException::new);

        // 2. 대출 기록 정보를 확인해 대출 중인지 확인
        if (userloanHistoryRepository.existsByBookNameAndIsReturn(book.getName(),false)){
            // 3. 만약에 확인했는데 대출 중이면 예외 발생
            throw new IllegalArgumentException("이미 대출되어있는 책");
        }
        // 4. 유저 정보 가져오기
        User user = userRepository.findByName(request.getUserName())
                .orElseThrow(IllegalArgumentException::new);
        //5. 유저 정보와 책 정보를 기반으로 UserLoanHistory를 저장
        // UserLoanHistory에 생성자 받기
//        userloanHistoryRepository.save(new UserLoanHistory(user, book.getName()));
        user.LoanBook(book.getName());
    }

    @Transactional
    public void returnBook(BookReturnRequest request) {
        // 1. 유저 정보 찾아서 유저 가져오기
        User user = userRepository.findByName(request.getUserName())
                .orElseThrow(IllegalArgumentException::new);
        user.returnBook(request.getBookName());
    }
}
