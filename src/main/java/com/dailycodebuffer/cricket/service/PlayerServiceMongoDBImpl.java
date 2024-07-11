package com.dailycodebuffer.cricket.service;

import com.dailycodebuffer.cricket.entity.PlayerEntity;
import com.dailycodebuffer.cricket.entity.PlayerMongoDBEntity;
import com.dailycodebuffer.cricket.exception.CricketAppServiceRuntimeException;
import com.dailycodebuffer.cricket.model.PlayerRequest;
import com.dailycodebuffer.cricket.model.PlayerResponse;
import com.dailycodebuffer.cricket.repository.PlayerMongoDBRepository;
import com.dailycodebuffer.cricket.repository.PlayerRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Log4j2
@Profile("mongodb")
public class PlayerServiceMongoDBImpl implements PlayerService{

    @Autowired
    private PlayerMongoDBRepository playerRepository;
    @Override
    public int addPlayer(PlayerRequest playerRequest) {
        log.info("Adding Player..");

        PlayerMongoDBEntity player = new PlayerMongoDBEntity();
        BeanUtils.copyProperties(playerRequest,player);
        player.setPlayerId(new Random().nextInt());
        playerRepository.save(player);

        log.info("Player Created");
        return player.getPlayerId();
    }

    @Override
    public PlayerResponse getPlayerById(int playerId) {
        log.info("Get the Player for playerId: {}", playerId);

        PlayerMongoDBEntity player
                = playerRepository.findById(playerId)
                .orElseThrow(
                        () -> new CricketAppServiceRuntimeException("Player with given id not found","NO_PLAYER_FOUND"));

        PlayerResponse playerResponse
                = new PlayerResponse();

        BeanUtils.copyProperties(player, playerResponse);

        return playerResponse;
    }

    @Override
    public List<PlayerResponse> getPlayers() {
        log.info("Get all Players");
        List<PlayerMongoDBEntity> playerEntities
                = playerRepository.findAll();

        List<PlayerResponse> playerResponses=
                playerEntities.stream()
                .map(playerEntity -> {
                    PlayerResponse playerResponse
                            = new PlayerResponse();
                    BeanUtils.copyProperties(playerEntity, playerResponse);
                    return playerResponse;
                })
                .collect(Collectors.toList());

        return playerResponses;
    }

    @Override
    public String deletePlayerById(int playerId) {
       playerRepository.deleteById(playerId);
       return "Player with PlayerId " + playerId + " deleted successfully!";
    }

    @Override
    public List<PlayerResponse> searchPlayer(String role) {
        log.info("Search Players");
        List<PlayerEntity> playerEntities
                = playerRepository.findAllByRole(role);

        List<PlayerResponse> playerResponses=
                playerEntities.stream()
                        .map(playerEntity -> {
                            PlayerResponse playerResponse
                                    = new PlayerResponse();
                            BeanUtils.copyProperties(playerEntity, playerResponse);
                            return playerResponse;
                        })
                        .collect(Collectors.toList());

        return playerResponses;
    }
}
