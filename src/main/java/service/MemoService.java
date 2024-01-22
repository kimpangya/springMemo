package service;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import com.sparta.memo.repository.MemoRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class MemoService {

    //외부에서 jdbc객체 받아옴
    //jdbc템플릿 사용가능
    private final JdbcTemplate jdbcTemplate;
    public MemoService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public MemoResponseDto createMemo(MemoRequestDto requestDto) {
        // RequestDto -> Entity 이건 서비스에서 해도 됨
        Memo memo = new Memo(requestDto);

        //DB 저장 레포지한테 맡기기
        MemoRepository memoRepository=new MemoRepository(jdbcTemplate);
        //저장할 메모객체 넘기고 반환값으로 메모 받음 이거 어차피 다시 responsedto로 줘야됨
        Memo saveMemo = memoRepository.save(memo);

        // Entity -> ResponseDto
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

        return memoResponseDto;
    }

    //DB조회
    public List<MemoResponseDto> getMemos() {
        MemoRepository memoRepository=new MemoRepository(jdbcTemplate);
                return memoRepository.findAll();
    }

    public Long updateMemo(Long id, MemoRequestDto requestDto) {
        MemoRepository memoRepository=new MemoRepository(jdbcTemplate);

        // 해당 메모가 DB에 존재하는지 확인
        Memo memo = memoRepository.findById(id);
        if(memo != null) {
            // memo 내용 수정
            memoRepository.update(id,requestDto);

            return id;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }



    public Long deleteMemo(Long id) {
        MemoRepository memoRepository=new MemoRepository(jdbcTemplate);

        // 해당 메모가 DB에 존재하는지 확인
        Memo memo = memoRepository.findById(id);
        if(memo != null) {
            memoRepository.delelte(id);
            return id;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

}

