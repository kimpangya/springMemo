package com.sparta.memo.controller;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//json데이터만 던져주면 됨. 따로 html 반환 안할거임
@RestController
@RequestMapping("/api")
public class MemoController {

    //id값 long = key값, 실제 메모객체 = values
    private final Map<Long, Memo> memoList=new HashMap<>();

    //json형태로 데이터 받을거니까 @RequestBody
    //requestDto엔 클라이언트가 보내온 데이터 담겨있는거임
    //메모 만들기 api CREATE
    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto){
        //RequestDto => Entity로 바꿔야 저장할 수 있음
        Memo memo=new Memo(requestDto);

        //Memo의 max id 찾아야됨
        //id값으로 메모 구분됨 중복 X 기본키
        //현재 데베 메모의 가장 최근값 +1 하면 max id
        //우리 아직 db안배웠으니까 그냥 Map 만들어서 쓰겠음
        //memoList.keySet()하면 key들을 가져옴 그걸 max()해서 가장 큰 키 값 찾아옴
        //데이터 하나도 없을 때는 1번부터 시작
        //id 넣어주기
        Long maxId = memoList.size() > 0 ? Collections.max(memoList.keySet()) +1 : 1;
        memo.setId(maxId);

        //db저장
        memoList.put(memo.getId(), memo);

        //Entity(Memo) => ResponseDto로 바꿔야됨
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

        return memoResponseDto;
    }

    //메모 조회하기 api GET
    //메모들 가져와야하니까 List형식으로 반환
    @GetMapping("/memos")
    public List<MemoResponseDto> getMemos(){
        //Map to List
        //우리가 지금 db대신 쓰고있는게 Map이잖음 그래서 바꿔주기
        //Map에서 values = Memo객체들 스트림으로 가져와서
        //map() 모두 돌면서 new MemoResponseDto객체들로 만들어줌
        //그리고 그걸 List에 넣기
        //MemoResponseDto에서 생성자를 호출함(기본생성자X 매개변수로 Memo인 애)
        List<MemoResponseDto> responseList=memoList.values().stream()
                .map(MemoResponseDto::new).toList();

        return responseList;
    }
}
