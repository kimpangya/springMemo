package com.sparta.memo.controller;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.repository.MemoRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import service.MemoService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MemoController {
        private final MemoService memoService;

        //만들어진 memoService를 외부에서 넣어주면 되는거임
        public MemoController(MemoService memoService) {
            this.memoService=memoService;
        }

    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) {
        // service에서도 jdbc쓰니까 생성자 있잖음 거기에 전달해줘
        // 보통 controller, service의 메소드명 일치시키는 경우 많음 편함
        //service에서 처리한 거 바로 client한테 줄 거임
        return memoService.createMemo(requestDto);

    }

    @GetMapping("/memos")
    public List<MemoResponseDto> getMemos() {
        return memoService.getMemos();
    }

    @PutMapping("/memos/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
        return memoService.updateMemo(id, requestDto);
    }

    @DeleteMapping("/memos/{id}")
    public Long deleteMemo(@PathVariable Long id) {
        return memoService.deleteMemo(id);
    }
}