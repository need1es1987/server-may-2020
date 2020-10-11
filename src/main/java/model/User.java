package model;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class User {
    private final String login;
    private final String password;
}
