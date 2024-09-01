package com.example.coursecanvasspring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.*;
import java.util.regex.*;
import java.util.Map;


@Service
public class NotionService {

    @Value("${notion.api.base.url:https://www.notion.so/api/v3}")
    private String apiBaseUrl;

    @Value("${notion.api.auth.token:random}")
    private String authToken;

    @Value("${notion.api.active.user:random}")
    private String activeUser;

    private final RestTemplate restTemplate;

    public NotionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public static String idToUuid(String id) {
        return id.substring(0, 8) + "-" +
                id.substring(8, 12) + "-" +
                id.substring(12, 16) + "-" +
                id.substring(16, 20) + "-" +
                id.substring(20);
    }


    public static String parsePageId(String id, boolean uuid) {
        Pattern pageIdRe = Pattern.compile("\\b([a-f0-9]{32})\\b");
        Pattern pageId2Re = Pattern.compile("\\b([a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12})\\b");

        if (id == null || id.isEmpty()) {
            return null;
        }

        id = id.split("\\?")[0];

        Matcher matcher = pageIdRe.matcher(id);
        if (matcher.find()) {
            return uuid ? idToUuid(matcher.group(1)) : matcher.group(1);
        }

        Matcher matcher2 = pageId2Re.matcher(id);
        if (matcher2.find()) {
            return uuid ? matcher2.group(1) : matcher2.group(1).replace("-", "");
        }

        return null;
    }


    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        if(!authToken.equals("random")){
            headers.set("Authorization", "Bearer " + authToken);
        }

        if (!activeUser.equals("random")) {
            headers.set("x-notion-active-user-header", activeUser);
        }
        return headers;
    }


    public Map<String, Object> getPage(String pageId, Map<String, Object> options) {

        // Default options
        Boolean fetchMissingBlocks = (Boolean) options.getOrDefault("fetchMissingBlocks", true);
        Boolean fetchCollections = (Boolean) options.getOrDefault("fetchCollections", true);
        Boolean signFileUrls = (Boolean) options.getOrDefault("signFileUrls", true);
        Integer chunkLimit = (Integer) options.getOrDefault("chunkLimit", 100);
        Integer chunkNumber = (Integer) options.getOrDefault("chunkNumber", 0);

        // API URL
        String url = UriComponentsBuilder.fromHttpUrl(apiBaseUrl).pathSegment("loadPageChunk").toUriString();
        String parsedId = parsePageId(pageId, true);

        // Request body
        Map<String, Object> body = Map.of(
                "pageId", parsedId,
                "limit", chunkLimit,
                "chunkNumber", chunkNumber,
                "cursor", Map.of("stack", new Object[]{}),
                "verticalColumns", false
        );

        HttpHeaders headers = createHeaders();
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);

        Map resBody = response.getBody();
        Map recordMap = (Map) resBody.get("recordMap");

        if(recordMap==null || recordMap.get("block")==null){
            throw new RuntimeException("Page not found");
        }

        recordMap.putIfAbsent("collection", new HashMap<>());
        recordMap.putIfAbsent("collection_view", new HashMap<>());
        recordMap.putIfAbsent("notion_user", new HashMap<>());
        recordMap.putIfAbsent("collection_query ", new HashMap<>());
        recordMap.putIfAbsent("signed_urls", new HashMap<>());

        if(fetchMissingBlocks){
            fetchMissingBlocks(recordMap, chunkLimit);
        }

        if (fetchCollections) {
            fetchCollections(recordMap);
        }

        if (signFileUrls) {
            fetchSignedUrls(recordMap);
        }

        return recordMap;
    }

    private void addContentBlocks(String blockId, Set<String> contentBlockIds, Map<String, Map> blockMap) {
        if(contentBlockIds.contains(blockId)){
            return;
        } else{
            contentBlockIds.add(blockId);
        }

        Map<String, Map> block = (Map<String, Map>) blockMap.get(blockId).get("value");
        if(block==null){
            return;
        }
    }


    private List<String> missingBlockIds(Map recordMap, String blockId) {
        String rootBlockId = blockId != null ? blockId : ((Map<String, Object>) recordMap.get("block")).keySet().iterator().next();
        Set<String> contentBlockIds = new HashSet<>();
        addContentBlocks(rootBlockId, contentBlockIds, (Map<String, Map>) recordMap.get("block"));

        return new ArrayList<>(contentBlockIds);
    }

    private void fetchMissingBlocks(Map recordMap, Integer chunkLimit) {
//        while (true){
//            List<String> missingBlockIds = missingBlockIds(recordMap, null);
//            if(missingBlockIds.isEmpty()){
//                break;
//            }
//        }
    }

    private void fetchCollections(Map recordMap) {
        // TODO: Implement this
    }

    private void fetchSignedUrls(Map recordMap) {
        // TODO: Implement this
    }


}
