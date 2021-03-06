package io.eberlein.smthnspcl.drinkteawithme;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.eberlein.smthnspcl.drinkteawithme.Static.CREATED;
import static io.eberlein.smthnspcl.drinkteawithme.Static.CUP_SIZE;
import static io.eberlein.smthnspcl.drinkteawithme.Static.CUP_SIZES;
import static io.eberlein.smthnspcl.drinkteawithme.Static.DISPLAY_NAME;
import static io.eberlein.smthnspcl.drinkteawithme.Static.EMAIL;
import static io.eberlein.smthnspcl.drinkteawithme.Static.FRIENDS;
import static io.eberlein.smthnspcl.drinkteawithme.Static.LAST_ONLINE;
import static io.eberlein.smthnspcl.drinkteawithme.Static.LAST_SESSION;
import static io.eberlein.smthnspcl.drinkteawithme.Static.OFFLINE_TIMELINE;
import static io.eberlein.smthnspcl.drinkteawithme.Static.ONLINE;
import static io.eberlein.smthnspcl.drinkteawithme.Static.ONLINE_TIMELINE;
import static io.eberlein.smthnspcl.drinkteawithme.Static.SESSIONS;
import static io.eberlein.smthnspcl.drinkteawithme.Static.TEA_TIME;
import static io.eberlein.smthnspcl.drinkteawithme.Static.USERNAME_TIMELINE;
import static io.eberlein.smthnspcl.drinkteawithme.Static.USERS;

public class User extends FireBaseObject {
    private String id;
    private String email;
    private Boolean online;
    private String displayName;
    private String lastOnline;
    private String created;
    private Integer teaTime;
    private Double cupSize;
    private List<Double> cupSizes;
    private String lastSession;
    private HashMap<String, String> friends;
    private List<Session> sessions;
    private List<String> onlineTimeline;
    private List<String> offlineTimeline;
    private HashMap<String, String> usernameTimeline;


    User(DocumentSnapshot snapshot) {
        id = snapshot.getId();
        online = snapshot.get(ONLINE, Boolean.class);
        email = snapshot.get(EMAIL, String.class);
        displayName = snapshot.get(DISPLAY_NAME, String.class);
        lastOnline = snapshot.get(LAST_ONLINE, String.class);
        created = snapshot.get(CREATED, String.class);
        lastSession = snapshot.get(LAST_SESSION, String.class);
        teaTime = snapshot.get(TEA_TIME, Integer.class);
        cupSize = snapshot.get(CUP_SIZE, Double.class);
        cupSizes = (List<Double>) snapshot.get(CUP_SIZES);
        if (cupSizes == null) cupSizes = new ArrayList<>();
        friends = (HashMap<String, String>) snapshot.get(FRIENDS);
        if (friends == null) friends = new HashMap<>();
        sessions = (List<Session>) snapshot.get(SESSIONS);
        if (sessions == null) sessions = new ArrayList<>();
        onlineTimeline = (List<String>) snapshot.get(ONLINE_TIMELINE);
        if (onlineTimeline == null) onlineTimeline = new ArrayList<>();
        offlineTimeline = (List<String>) snapshot.get(OFFLINE_TIMELINE);
        if (offlineTimeline == null) offlineTimeline = new ArrayList<>();
        usernameTimeline = (HashMap<String, String>) snapshot.get(USERNAME_TIMELINE);
        if (usernameTimeline == null) usernameTimeline = new HashMap<>();
    }

    User(String displayName, String email, String lastSession) {
        this.id = Static.hash(email);
        this.email = email;
        this.displayName = displayName;
        online = true;
        lastOnline = Static.getCurrentTimestamp();
        created = lastOnline;
        this.lastSession = lastSession;
        friends = new HashMap<>();
        teaTime = 30;
        cupSize = 0.4;
        cupSizes = new ArrayList<>();
        cupSizes.add(cupSize);
        sessions = new ArrayList<>();
        onlineTimeline = new ArrayList<>();
        offlineTimeline = new ArrayList<>();
        usernameTimeline = new HashMap<>();
        update();
    }

    @Override
    public void update() {
        FirebaseFirestore.getInstance().collection(USERS).document(id).set(this);
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public HashMap<String, String> getFriends() {
        return friends;
    }

    public void addFriend(String friend) {
        if (!friends.containsKey(friend)) {
            friends.put(friend, Static.getCurrentTimestamp());
            update();
        }
    }

    public void removeFriend(String friend) {
        if (friends.containsKey(friend)) {
            friends.remove(friend);
            update();
        }
    }

    public Double getCupSize() {
        return cupSize;
    }

    public void addCupSize(Double size) {
        if (!cupSizes.contains(size)) {
            cupSize = size;
            cupSizes.add(size);
            update();
        }
    }

    public void removeCupSize(Double size) {
        if (cupSizes.contains(size)) {
            cupSizes.remove(size);
            update();
        }
    }

    public List<Double> getCupSizes() {
        return cupSizes;
    }

    public Integer getTeaTime() {
        return teaTime;
    }

    public void setTeaTime(Integer teaTime) {
        this.teaTime = teaTime;
        update();
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline() {
        online = true;
        onlineTimeline.add(Static.getCurrentTimestamp());
        update();
    }

    public String getCreated() {
        return created;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
        update();
    }

    public String getLastOnline() {
        return lastOnline;
    }

    public void setLastOnline(String lastOnline) {
        this.lastOnline = lastOnline;
        update();
    }

    public String getLastSession() {
        return lastSession;
    }


    public void addSessionNow(String teaName, Double liter) {
        lastSession = Static.getCurrentTimestamp();
        this.sessions.add(new Session(lastSession, teaName, liter));
        update();
    }

    public List<String> getOnlineTimeline() {
        return onlineTimeline;
    }

    public Integer getSessionCount() {
        return sessions.size();
    }

    public void setOffline() {
        online = false;
        offlineTimeline.add(Static.getCurrentTimestamp());
        update();
    }
}
