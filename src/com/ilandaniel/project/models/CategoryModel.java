package com.ilandaniel.project.models;

import com.ilandaniel.project.classes.Category;
import com.ilandaniel.project.exceptions.ProjectException;
import com.ilandaniel.project.helpers.Helper;
import com.ilandaniel.project.interfaces.IValidator;
import com.ilandaniel.project.validators.CategoryValidator;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class CategoryModel {

    private final IValidator validator;

    public CategoryModel() {
        validator = new CategoryValidator();
    }


    /**
     * getting category from DB by the category_id.
     */
    public static Category getCategoryById(int id) throws ProjectException {
        Category category = null;
        try {
            category = new Category();
            HttpClient httpClient = HttpClient.newBuilder().build();
            HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/category/getCategoryById/" + id)).GET().build();
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            JSONObject jsonObject = new JSONObject(httpResponse.body());
            category.setName(jsonObject.getString("name"));
            category.setId(jsonObject.getInt("id"));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return category;
    }


    public int getCategoryIdByName(String categoryName) throws ProjectException {
        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/category/getCategoryIdByName/" + categoryName)).GET().build();
        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.statusCode() == HttpURLConnection.HTTP_OK) {
                return Integer.parseInt(httpResponse.body());
            } else {
                throw new ProjectException(httpResponse.body());
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return -1;
    }

    /**
     * add new category into the DB.
     */
    public String addCategory(Category category) throws ProjectException {
        String errors = validator.validate(category);
        if (errors.isEmpty()) {
            JSONObject jsonObject = new JSONObject(category);
            jsonObject.put("current_account_id", Helper.loggedInAccount.getId());
            HttpClient httpClient = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/category/addCategory"))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString()))
                    .build();
            try {
                HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                if (httpResponse.statusCode() == HttpURLConnection.HTTP_INTERNAL_ERROR) {
                    throw new ProjectException(httpResponse.body());
                }
                errors += httpResponse.body();

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return errors;
    }


    /**
     * delete category from the DB
     */
    public boolean deleteCategory(String categoryName) throws ProjectException {
        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/category/deleteCategory/" + categoryName + "/" + Helper.loggedInAccount.getId()))
                .DELETE()
                .build();

        try {
            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.statusCode() == HttpURLConnection.HTTP_BAD_REQUEST) {
                throw new ProjectException(httpResponse.body());
            } else if (httpResponse.statusCode() == HttpURLConnection.HTTP_INTERNAL_ERROR) {
                return false;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    public List<String> getAllCategories() throws ProjectException {
        List<String> list = new ArrayList<>();
        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/category/getAllCategories/" + Helper.loggedInAccount.getId())).GET().build();
        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            JSONArray jsonArray = new JSONArray(httpResponse.body());
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(jsonArray.getString(i));
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new ProjectException(e.getMessage());
        }
        return list;
    }

}
