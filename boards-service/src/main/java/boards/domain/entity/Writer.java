package boards.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Writer {

    private final Integer id;

    private final String name;

    private final String major;

}
