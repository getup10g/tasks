package com.crud.tasks.mapper;

import com.crud.tasks.config.TrelloConfig;
import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloList;
import com.crud.tasks.domain.TrelloListDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
public class TrelloMapperTest {
    @InjectMocks
    private TrelloClient trelloClient;
    @InjectMocks
    private TrelloMapper trelloMapper;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private TrelloConfig trelloConfig;

    @Before
    public void init() {
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        when(trelloConfig.getTrelloToken()).thenReturn("test");
    }

    @Test
    public void mapToBoard() throws URISyntaxException {
        // Given
        TrelloBoardDto[] listTrelloBoardDto = new TrelloBoardDto[1];
        List<TrelloListDto> listTrelloListDto = new ArrayList<>();
        TrelloListDto trelloListDto = new TrelloListDto("id", "name", false);
        listTrelloListDto.add(trelloListDto);
        listTrelloBoardDto[0] = new TrelloBoardDto("id", "name", listTrelloListDto);
        URI uri = new URI("http://test.com/members/getup15/boards?key=test&token=test&fields=name,id&lists=all");
        when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(listTrelloBoardDto);

        //When
        List<TrelloBoardDto> trelloBoardsDto = trelloClient.getTrelloBoards();
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardsDto);

        //Then
        assertEquals(trelloBoards.size(), trelloBoardsDto.size());
        assertEquals(trelloBoards.get(0).getId(), trelloBoardsDto.get(0).getId());
        assertEquals(trelloBoards.get(0).getName(), trelloBoardsDto.get(0).getName());
        assertEquals(trelloBoards.get(0).getLists().get(0).getId(), trelloBoardsDto.get(0).getLists().get(0).getId());
    }
    @Test
    public void mapToBoardDto() throws URISyntaxException {
        // Given
        TrelloBoardDto[] listTrelloBoardDto = new TrelloBoardDto[1];
        List<TrelloListDto> listTrelloListDto = new ArrayList<>();
        TrelloListDto trelloListDto = new TrelloListDto("id", "name", false);
        listTrelloListDto.add(trelloListDto);
        listTrelloBoardDto[0] = new TrelloBoardDto("id", "name", listTrelloListDto);
        URI uri = new URI("http://test.com/members/getup15/boards?key=test&token=test&fields=name,id&lists=all");
        when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(listTrelloBoardDto);

        List<TrelloBoard> listTrelloBoard = new ArrayList<>();
        List<TrelloList> listTrelloList = new ArrayList<>();
        TrelloList trelloList = new TrelloList("id", "name", false);
        listTrelloList.add(trelloList);
        listTrelloBoard.add(new TrelloBoard("id", "name", listTrelloList));
        //When
        List<TrelloBoardDto> trelloBoardsDto = trelloClient.getTrelloBoards();
        List<TrelloBoardDto> trelloBoardsDto2 = trelloMapper.mapToBoardsDto(listTrelloBoard);

        //Then
        assertEquals(trelloBoardsDto2.size(), trelloBoardsDto.size());
        assertEquals(trelloBoardsDto2.get(0).getId(), trelloBoardsDto.get(0).getId());
        assertEquals(trelloBoardsDto2.get(0).getName(), trelloBoardsDto.get(0).getName());
        assertEquals(trelloBoardsDto2.get(0).getLists().get(0).getId(), trelloBoardsDto.get(0).getLists().get(0).getId());
    }

    @Test
    public void mapToListDto() throws URISyntaxException {
        // Given
        List<TrelloList> listTrelloList = new ArrayList<>();
        TrelloList trelloList = new TrelloList("id", "name", false);
        //When
        listTrelloList.add(trelloList);
        List<TrelloListDto> listTrelloListDto = trelloMapper.mapToListDto(listTrelloList);
        //Then
        assertEquals(listTrelloList.size(), listTrelloListDto.size());
        assertEquals(listTrelloList.get(0).getId(), listTrelloListDto.get(0).getId());
        assertEquals(listTrelloList.get(0).getName(), listTrelloListDto.get(0).getName());
    }
    @Test
    public void mapToList() throws URISyntaxException {
        // Given
        List<TrelloListDto> listTrelloListDto = new ArrayList<>();
        TrelloListDto trelloListDto = new TrelloListDto("id", "name", false);
        //When
        listTrelloListDto.add(trelloListDto);
        List<TrelloList> listTrelloList = trelloMapper.mapToList(listTrelloListDto);
        //Then
        assertEquals(listTrelloList.size(), listTrelloListDto.size());
        assertEquals(listTrelloList.get(0).getId(), listTrelloListDto.get(0).getId());
        assertEquals(listTrelloList.get(0).getName(), listTrelloListDto.get(0).getName());
    }
}
