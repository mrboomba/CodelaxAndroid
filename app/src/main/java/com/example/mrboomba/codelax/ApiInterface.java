package com.example.mrboomba.codelax;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by mrboomba on 8/12/2559.
 */

public interface ApiInterface {

    @GET("api/lesson/video/{num}")
    Call<VideoResponse> getMovie(@Path("num") int num );
    @GET("api/user/{num}/friandmat")
    Call<User> getList(@Path("num") String num );
    @GET("api/lesson/{num}")
    Call<LessonResponse> getLesson(@Path("num") int num );
    @GET("api/challenge/{num}")
    Call<ChallengeResponse> getChallenge(@Path("num") int num );
    @GET("api/user/{num}/lesson")
    Call<User> getUserLesson(@Path("num") String num );
    @GET("api/user/{num}/challenge")
    Call<User> getUserChallenge(@Path("num") int num );
    @GET("api/user/{num}/challenge")
    Call<User> getUserChallenge(@Path("num") String num );
    @GET("api/user/updatelesson/{username}/{num}")
    Call<User> updateUserLesson(@Path("username") String username,@Path("num") int num );
    @POST("api/login")
    Call<Login> login(@Body Login login);
    @POST("api/match/create")
    Call<Match> createMatch(@Body Match match);
    @GET("api/match/{num}")
    Call<Match> isReqest(@Path("num") String num );
    @GET("api/user/random")
    Call<User> random();
    @POST("api/match/finish")
    Call<Match> finish(@Body Match match);
    @POST("api/match/result")
    Call<Match> result(@Body Match match);
    @POST("api/match/end")
    Call<Match> end(@Body Match match);
    @POST("api/user/{number}/addfriend")
    Call<User> addFriend(@Body User user,@Path("number") String num);

}