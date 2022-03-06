package boards.outDomain;

import boards.domain.entity.Writer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
@RequiredArgsConstructor
public class httpWriterRepository implements WriterRepository {

    private final RestTemplate restTemplate;

    public Writer getWriter(Integer id) {
        return restTemplate.getForObject("http://members?id={}", Writer.class, id);
    }

}
