package com.sparta.memo.entity;

import com.sparta.memo.dto.MemoRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Memo {
    private Long id; //기본키
    private String username;
    private String contents;

    //requestDto 클라한테 받아온 데이터로 초기화 해줌
    //저기에 Getter 있으니까 ㄱㅊ음
    public Memo(MemoRequestDto requestDto) {
        this.username= requestDto.getUsername();
        this.contents= requestDto.getContents();
    }

    public void update(MemoRequestDto requestDto) {
        this.username=requestDto.getUsername();
        this.contents=requestDto.getContents();
    }
}