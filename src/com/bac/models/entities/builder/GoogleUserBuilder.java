package com.bac.models.entities.builder;

import com.bac.models.entities.Account;
import com.bac.models.entities.GoogleUser;

public final class GoogleUserBuilder {
    private String gmail;
    private String username;
    private Account account;

    private GoogleUserBuilder() {
    }

    public static GoogleUserBuilder aGoogleUser() {
        return new GoogleUserBuilder();
    }

    public GoogleUserBuilder withGmail(String gmail) {
        this.gmail = gmail;
        return this;
    }

    public GoogleUserBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public GoogleUserBuilder withAccount(Account account) {
        this.account = account;
        return this;
    }

    public GoogleUser build() {
        GoogleUser googleUser = new GoogleUser();
        googleUser.setGmail(gmail);
        googleUser.setUsername(username);
        googleUser.setAccount(account);
        return googleUser;
    }
}
