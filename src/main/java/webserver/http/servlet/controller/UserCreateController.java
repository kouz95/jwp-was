package webserver.http.servlet.controller;

import db.DataBase;
import model.User;
import utils.ModelMapper;
import webserver.http.request.HttpParams;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.servlet.AbstractView;
import webserver.http.servlet.RedirectView;

public class UserCreateController implements Controller {
    @Override
    public AbstractView doService(HttpRequest request, HttpResponse response) {
        HttpParams bodyParams = HttpParams.of(request.getBody());

        User user = ModelMapper.toModel(User.class, bodyParams);
        DataBase.addUser(user);

        return new RedirectView("/index.html");
    }
}