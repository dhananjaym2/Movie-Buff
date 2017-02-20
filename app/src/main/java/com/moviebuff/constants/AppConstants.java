package com.moviebuff.constants;

/**
 * Created by Admin on 19-02-2017.
 */

public class AppConstants {

    private static final String TMDB_BASE_URL_LISTING = "https://api.themoviedb.org/4/";
    public static final String TMDB_API_ACCESS_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjYjE4YjFkMGE2NzNiYzdlZmYyZTQ3NDA3MDZlNDY0NCIsInN1YiI6IjU4YTg2N2I1OTI1MTQxNTY2OTAwOTRmYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.kwV3Z7kXU5y1Uds060EAYj3tC72UI-6lcuSLXaNVscw";

    public static final String GET_URL_MOVIES_LIST = TMDB_BASE_URL_LISTING + "list/1";
    public static String BEARER_WITH_ACCESS_TOKEN_Authorization_HEADER_VALUE = "Bearer " + TMDB_API_ACCESS_TOKEN;
    public static String Authorization_HEADER_KEY = "Authorization";
}