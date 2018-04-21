package com.crud.tasks.trello.client;

import com.crud.tasks.domain.TrelloBoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TrelloClient {

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;

    @Value("${trello.app.username}")
    private String trelloUsername;

    @Value("${trello.app.key}")
    private String trelloAppKey;

    @Value("${trello.app.token}")
    private String trelloToken;

    @Autowired
    private RestTemplate restTemplate;

    private URI getURLForBoards() {

        URI url = UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/" + trelloUsername + "/boards?")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("fields", "name,id").build().encode().toUri();

        return url;
    }

    public Optional<List> getTrelloBoards() {

        URI url = UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/" + trelloUsername + "/boards?")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("fields", "name,id").build().encode().toUri();

        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(getURLForBoards(), TrelloBoardDto[].class);

        if (boardsResponse != null) {
            List<TrelloBoardDto> theResult = Arrays.asList(boardsResponse).stream()
                    .filter(s -> s.getId() != null)
                    .filter(s -> s.getName() != null)
                    .filter(s -> s.getName().contains("Kodilla"))
                    .collect(Collectors.toList());

            return Optional.ofNullable(theResult);
        }

        return null;
    }

}

