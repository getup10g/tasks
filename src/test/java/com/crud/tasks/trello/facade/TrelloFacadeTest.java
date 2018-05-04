package com.crud.tasks.trello.facade;

import com.crud.tasks.config.TrelloConfig;
import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloList;
import com.crud.tasks.domain.TrelloListDto;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.client.TrelloClient;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloFacadeTest {
    @InjectMocks
    private TrelloFacade trelloFacade;
    @Mock
    private TrelloMapper trelloMapper;
    @Mock
    private TrelloValidator trelloValidator;
    @Mock
    private TrelloService trelloService;

    @Test
    public void shouldFetchEmpltyList() {
        //given
        List<TrelloListDto> trelloList = new ArrayList<>();
        trelloList.add(new TrelloListDto("id","test_List",false));

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("1","test",trelloList));

        List<TrelloList> mappedTrelloLists = new ArrayList<>();
        mappedTrelloLists.add(new TrelloList("1","test_list", false));

        List<TrelloBoard> mappedTrelloBoards = new ArrayList<>();
        mappedTrelloBoards.add(new TrelloBoard("1","test",mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(trelloMapper.mapToBoards(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(trelloMapper.mapToBoardsDto(anyList())).thenReturn(new ArrayList());
        when(trelloValidator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(new ArrayList());

    //when
        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchTrelloBoards();
        //then
        assertNotNull(trelloBoardDtos);
        assertEquals(0,trelloBoardDtos.size());

    }

    @Test
    public void shouldFetchList() {
        //given
        List<TrelloListDto> trelloList = new ArrayList<>();
        trelloList.add(new TrelloListDto("1","test_list",false));

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("1","test",trelloList));

        List<TrelloList> mappedTrelloLists = new ArrayList<>();
        mappedTrelloLists.add(new TrelloList("1","test_list", false));

        List<TrelloBoard> mappedTrelloBoards = new ArrayList<>();
        mappedTrelloBoards.add(new TrelloBoard("1","test",mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(trelloMapper.mapToBoards(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(trelloMapper.mapToBoardsDto(anyList())).thenReturn(trelloBoards);
        when(trelloValidator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(mappedTrelloBoards);

        //when
        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchTrelloBoards();
        //then
        assertNotNull(trelloBoardDtos);
        assertEquals(1,trelloBoardDtos.size());

        trelloBoardDtos.forEach(trelloBoardDto -> {
            assertEquals("1",trelloBoardDto.getId());
            assertEquals("test",trelloBoardDto.getName());

            trelloBoardDto.getLists().forEach(trelloListDto -> {
                assertEquals("1", trelloListDto.getId());
                assertEquals("test_list", trelloListDto.getName());
                assertEquals(false, trelloListDto.isClosed());
            });

        });
    }
}
