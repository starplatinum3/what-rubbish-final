
package com.example.whatrubbish.db;

import android.content.Context;

import com.example.whatrubbish.repository.ArticleRepository;
import com.example.whatrubbish.repository.BasketRepository;
import com.example.whatrubbish.repository.CardRepository;
import com.example.whatrubbish.repository.CardGameRepository;
import com.example.whatrubbish.repository.CityRepository;
import com.example.whatrubbish.repository.ColeFragGameNowRepository;
import com.example.whatrubbish.repository.ColeFragGameStatRepository;
import com.example.whatrubbish.repository.FriendshipRepository;
import com.example.whatrubbish.repository.GameRepository;
import com.example.whatrubbish.repository.GameHonorRepository;
import com.example.whatrubbish.repository.GameRecordRepository;
import com.example.whatrubbish.repository.PlaceRepository;
import com.example.whatrubbish.repository.PresentRepository;
import com.example.whatrubbish.repository.PsnExchgRecRepository;
import com.example.whatrubbish.repository.RubbishRepository;
import com.example.whatrubbish.repository.RubbishTypeRepository;
import com.example.whatrubbish.repository.RubTyCorespRepository;
import com.example.whatrubbish.repository.ShootGameRepository;
import com.example.whatrubbish.repository.SignInHonorRepository;
import com.example.whatrubbish.repository.SignInStdRepository;
import com.example.whatrubbish.repository.UserRepository;
import com.example.whatrubbish.repository.WikiHistoryRepository;

import lombok.Data;

@Data
public class Repository {

    Context context;
    AppDatabase database;

    ArticleRepository articleRepository;

    BasketRepository basketRepository;

    CardRepository cardRepository;

    CardGameRepository cardGameRepository;

    CityRepository cityRepository;

    ColeFragGameNowRepository coleFragGameNowRepository;

    ColeFragGameStatRepository coleFragGameStatRepository;

    FriendshipRepository friendshipRepository;

    GameRepository gameRepository;

    GameHonorRepository gameHonorRepository;

    GameRecordRepository gameRecordRepository;

    PlaceRepository placeRepository;

    PresentRepository presentRepository;

    PsnExchgRecRepository psnExchgRecRepository;

    RubbishRepository rubbishRepository;

    RubbishTypeRepository rubbishTypeRepository;

    RubTyCorespRepository rubTyCorespRepository;

    ShootGameRepository shootGameRepository;

    SignInHonorRepository signInHonorRepository;

    SignInStdRepository signInStdRepository;

    UserRepository userRepository;

    WikiHistoryRepository wikiHistoryRepository;


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public AppDatabase getDatabase() {
        return database;
    }

    public void setDatabase(AppDatabase database) {
        this.database = database;
    }

    public ArticleRepository getArticleRepository() {
        return articleRepository;
    }

    public void setArticleRepository(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public BasketRepository getBasketRepository() {
        return basketRepository;
    }

    public void setBasketRepository(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    public CardRepository getCardRepository() {
        return cardRepository;
    }

    public void setCardRepository(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public CardGameRepository getCardGameRepository() {
        return cardGameRepository;
    }

    public void setCardGameRepository(CardGameRepository cardGameRepository) {
        this.cardGameRepository = cardGameRepository;
    }

    public CityRepository getCityRepository() {
        return cityRepository;
    }

    public void setCityRepository(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public ColeFragGameNowRepository getColeFragGameNowRepository() {
        return coleFragGameNowRepository;
    }

    public void setColeFragGameNowRepository(ColeFragGameNowRepository coleFragGameNowRepository) {
        this.coleFragGameNowRepository = coleFragGameNowRepository;
    }

    public ColeFragGameStatRepository getColeFragGameStatRepository() {
        return coleFragGameStatRepository;
    }

    public void setColeFragGameStatRepository(ColeFragGameStatRepository coleFragGameStatRepository) {
        this.coleFragGameStatRepository = coleFragGameStatRepository;
    }

    public FriendshipRepository getFriendshipRepository() {
        return friendshipRepository;
    }

    public void setFriendshipRepository(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    public GameRepository getGameRepository() {
        return gameRepository;
    }

    public void setGameRepository(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public GameHonorRepository getGameHonorRepository() {
        return gameHonorRepository;
    }

    public void setGameHonorRepository(GameHonorRepository gameHonorRepository) {
        this.gameHonorRepository = gameHonorRepository;
    }

    public GameRecordRepository getGameRecordRepository() {
        return gameRecordRepository;
    }

    public void setGameRecordRepository(GameRecordRepository gameRecordRepository) {
        this.gameRecordRepository = gameRecordRepository;
    }

    public PlaceRepository getPlaceRepository() {
        return placeRepository;
    }

    public void setPlaceRepository(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public PresentRepository getPresentRepository() {
        return presentRepository;
    }

    public void setPresentRepository(PresentRepository presentRepository) {
        this.presentRepository = presentRepository;
    }

    public PsnExchgRecRepository getPsnExchgRecRepository() {
        return psnExchgRecRepository;
    }

    public void setPsnExchgRecRepository(PsnExchgRecRepository psnExchgRecRepository) {
        this.psnExchgRecRepository = psnExchgRecRepository;
    }

    public RubbishRepository getRubbishRepository() {
        return rubbishRepository;
    }

    public void setRubbishRepository(RubbishRepository rubbishRepository) {
        this.rubbishRepository = rubbishRepository;
    }

    public RubbishTypeRepository getRubbishTypeRepository() {
        return rubbishTypeRepository;
    }

    public void setRubbishTypeRepository(RubbishTypeRepository rubbishTypeRepository) {
        this.rubbishTypeRepository = rubbishTypeRepository;
    }

    public RubTyCorespRepository getRubTyCorespRepository() {
        return rubTyCorespRepository;
    }

    public void setRubTyCorespRepository(RubTyCorespRepository rubTyCorespRepository) {
        this.rubTyCorespRepository = rubTyCorespRepository;
    }

    public ShootGameRepository getShootGameRepository() {
        return shootGameRepository;
    }

    public void setShootGameRepository(ShootGameRepository shootGameRepository) {
        this.shootGameRepository = shootGameRepository;
    }

    public SignInHonorRepository getSignInHonorRepository() {
        return signInHonorRepository;
    }

    public void setSignInHonorRepository(SignInHonorRepository signInHonorRepository) {
        this.signInHonorRepository = signInHonorRepository;
    }

    public SignInStdRepository getSignInStdRepository() {
        return signInStdRepository;
    }

    public void setSignInStdRepository(SignInStdRepository signInStdRepository) {
        this.signInStdRepository = signInStdRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public WikiHistoryRepository getWikiHistoryRepository() {
        return wikiHistoryRepository;
    }

    public void setWikiHistoryRepository(WikiHistoryRepository wikiHistoryRepository) {
        this.wikiHistoryRepository = wikiHistoryRepository;
    }

    public Repository(Context context) {
        this.context = context;
        initDatabase(context);
    }

    void initDatabase(Context context) {
        if (database == null) {
            database = AppDatabase.getDatabase(context);
        }

        if (articleRepository == null) {
            articleRepository = new ArticleRepository(database.articleDao());
        }

        if (basketRepository == null) {
            basketRepository = new BasketRepository(database.basketDao());
        }

        if (cardRepository == null) {
            cardRepository = new CardRepository(database.cardDao());
        }

        if (cardGameRepository == null) {
            cardGameRepository = new CardGameRepository(database.cardGameDao());
        }

        if (cityRepository == null) {
            cityRepository = new CityRepository(database.cityDao());
        }

        if (coleFragGameNowRepository == null) {
            coleFragGameNowRepository = new ColeFragGameNowRepository(database.coleFragGameNowDao());
        }

        if (coleFragGameStatRepository == null) {
            coleFragGameStatRepository = new ColeFragGameStatRepository(database.coleFragGameStatDao());
        }

        if (friendshipRepository == null) {
            friendshipRepository = new FriendshipRepository(database.friendshipDao());
        }

        if (gameRepository == null) {
            gameRepository = new GameRepository(database.gameDao());
        }

        if (gameHonorRepository == null) {
            gameHonorRepository = new GameHonorRepository(database.gameHonorDao());
        }

        if (gameRecordRepository == null) {
            gameRecordRepository = new GameRecordRepository(database.gameRecordDao());
        }

        if (placeRepository == null) {
            placeRepository = new PlaceRepository(database.placeDao());
        }

        if (presentRepository == null) {
            presentRepository = new PresentRepository(database.presentDao());
        }

        if (psnExchgRecRepository == null) {
            psnExchgRecRepository = new PsnExchgRecRepository(database.psnExchgRecDao());
        }

        if (rubbishRepository == null) {
            rubbishRepository = new RubbishRepository(database.rubbishDao());
        }

        if (rubbishTypeRepository == null) {
            rubbishTypeRepository = new RubbishTypeRepository(database.rubbishTypeDao());
        }

        if (rubTyCorespRepository == null) {
            rubTyCorespRepository = new RubTyCorespRepository(database.rubTyCorespDao());
        }

        if (shootGameRepository == null) {
            shootGameRepository = new ShootGameRepository(database.shootGameDao());
        }

        if (signInHonorRepository == null) {
            signInHonorRepository = new SignInHonorRepository(database.signInHonorDao());
        }

        if (signInStdRepository == null) {
            signInStdRepository = new SignInStdRepository(database.signInStdDao());
        }

        if (userRepository == null) {
            userRepository = new UserRepository(database.userDao());
        }

        if (wikiHistoryRepository == null) {
            wikiHistoryRepository = new WikiHistoryRepository(database.wikiHistoryDao());
        }

    }
}

