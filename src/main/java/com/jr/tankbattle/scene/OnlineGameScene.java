package com.jr.tankbattle.scene;

import com.jr.tankbattle.client.Client;
import com.jr.tankbattle.client.Message;
import com.jr.tankbattle.controller.Account;
import com.jr.tankbattle.controller.GameDlg;
import com.jr.tankbattle.controller.MapScr;
import com.jr.tankbattle.entity.*;
import com.jr.tankbattle.util.MapData;
import com.jr.tankbattle.util.TankType;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.DatagramSocket;
import java.nio.channels.AcceptPendingException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class OnlineGameScene implements Client.FireStatusListener {
    @FXML
    private Canvas canvas = new Canvas(1080, 720);
    private GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

    private boolean running = false;
    private Map<String, Tank3> playerTanks = new HashMap<>();
    private List<String> playerList;
    private Tank3 playerTank1;
    private Tank3 playerTank2;
    private Tank3 playerTank3;
    private Tank3 playerTank4;

    private MapData map = new MapData(this);
    public Map<String, Bullet> bullets = new HashMap<>();
    public List<AiTank> aiTanks = new ArrayList<>();
    public List<Tree> trees = new ArrayList<>();
    public List<Rock> rocks = new ArrayList<>();
    public List<Sheild> sheilds = new ArrayList<>();
    public List<Iron> irons = new ArrayList<>();
    public List<Heart> hearts = new ArrayList<>();
    public List<Pool> pools = new ArrayList<>();
    public List<Landmine> landmines = new ArrayList<>();
    public List<Explode> explodes = new ArrayList<>();

    private static final ExecutorService executor = Executors.newFixedThreadPool(2);
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private long lastSendTime = 0;
    private long lastReceiveTime = 0;
    private final long GAME_DATA_SEND_INTERVAL_MS = 500; // 发送间隔
    private final long GAME_DATA_RECEIVE_INTERVAL_MS = 500; // 接收间隔
    private Image backImage0 = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/bkg5.jpg"));
    public Image backgroundImage = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/background1.jpg")));
    private Client client = new Client();

    /*----------------------------------------------*/
    @Override
    public void onFireStatusReceived(String tankId, boolean isFire) {
        Tank3 tank = playerTanks.get(tankId);
        if (tank != null) {
            if (isFire) {
                tank.openFire(); // 假设 Tank3 类有一个 startFiring 方法
            }
        }
    }

    // 启动消息接收线程并传入当前实例作为监听器
    private void startMessageReceiver() {
        try {
            DatagramSocket socket = new DatagramSocket(); // 根据实际情况初始化
            Client client = new Client(); // 创建或获取现有 Client 实例
            client.start(); // 启动客户端
            Client.MessageReceiver receiver = client.new MessageReceiver(socket, this);
            executor.execute(receiver); // 使用 Executor 启动接收线程
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
// 将 playerTanks 地图序列化为字符串
    private void sendGameData() {
        try {
            // 使用 StringBuilder 构建玩家坦克数据字符串，只包含当前玩家ID相关的坦克数据
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Tank3> entry : playerTanks.entrySet()) {
                String playerId = entry.getKey();
                Tank3 tank = entry.getValue();

                if (playerId.equals(Account.uid)) {
                    sb.append(tank.toDataString());
                }
            }
            client.sendPlayerTanksData(sb.toString());

//            // 只发送以当前玩家 ID 开头的子弹数据
//            StringBuilder sbBullets = new StringBuilder();
//            for (Bullet bullet : bullets.values()) {
//                String bulletId = bullet.getId(); // 获取子弹 ID
//
//                // 检查 bulletId 是否为 null
//                if (bulletId != null && bulletId.startsWith(Account.uid)) {
//                    if (sbBullets.length() > 0) {
//                        sbBullets.append(";");
//                    }
//                    sbBullets.append(bullet.toDataString());
//                }
//            }
//            client.sendBulletsData(sbBullets.toString());
        } catch (Exception e) {
            e.printStackTrace(); // 处理或记录异常
        }
    }

    private void getGameData() {
        try {
            String data = client.getPlayerTanksData();
            if (!Objects.equals(data, "")) {
                setPlayerTanks(data);
            }
//            String data1 = client.getBulletsData();
//            if (!Objects.equals(data1, "")) {
//                setBullets(data1);
//            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle or log the exception
        }
    }


    // 从字符串反序列化回 playerTanks 地图
    public void setPlayerTanks(String dataString) {
        // 创建一个临时的地图来存储更新后的坦克数据
        Map<String, Tank3> updatedPlayerTanks = new HashMap<>(playerTanks);
        String[] tankDataStrings = dataString.split(";");
        for (String tankDataString : tankDataStrings) {
            if (!tankDataString.isEmpty()) {
                Tank3 tank = Tank3.fromDataString(tankDataString);
                // 更新临时地图中的坦克数据
                updatedPlayerTanks.put(tank.getPlayerId(), tank);
            }
        }

        // 确保保留当前玩家的坦克数据
        if (playerTanks.containsKey(Account.uid)) {
            updatedPlayerTanks.put(Account.uid, playerTanks.get(Account.uid));
        }

        // 替换 playerTanks
        playerTanks.clear();
        playerTanks.putAll(updatedPlayerTanks);


        // 遍历玩家 ID，将对应的坦克分配到 playerTankX 字段
        for (Map.Entry<String, Tank3> entry : playerTanks.entrySet()) {

            String playerId = entry.getKey();
            Tank3 tank = entry.getValue();
            if (playerId.equals(playerTank1.getPlayerId())) {
                playerTank1 = tank;
            } else if (playerId.equals(playerTank2.getPlayerId())) {
                playerTank2 = tank;
            } else if (playerId.equals(playerTank3.getPlayerId())) {
                playerTank3 = tank;
            } else if (playerId.equals(playerTank4.getPlayerId())) {
                playerTank4 = tank;
            }
        }
    }


    private void setBullets(String dataString) {
        Map<String, Bullet> updatedBullets = new HashMap<>();

        // 按分号分隔数据段
        String[] bulletDataStrings = dataString.split(";");
        for (String bulletDataString : bulletDataStrings) {
            if (!bulletDataString.isEmpty()) {
                // 按逗号分隔数据部分
                String[] bulletAttributes = bulletDataString.split(",");

                // 检查数据段是否包含足够的部分（至少包括ID和其他属性）
                if (bulletAttributes.length > 1) {
                    String id = bulletAttributes[0];
                    // 如果 bullets 中存在该 ID 的子弹，则更新
                    if (bullets.containsKey(id)) {
                        Bullet bullet = Bullet.fromDataString(bulletDataString);
                        updatedBullets.put(id, bullet);
                    }
                }
            }
        }

        // 仅更新现有子弹中的条目
        for (Map.Entry<String, Bullet> entry : updatedBullets.entrySet()) {
            bullets.put(entry.getKey(), entry.getValue());
        }
    }



    /*----------------------------------------------*/
    public void init(Stage stage, List<String> playerList) {
        AnchorPane root = new AnchorPane(canvas);
        stage.getScene().setRoot(root);
        //设置键盘事件
        stage.getScene().setOnKeyReleased(event1 -> {
            try {
                handleKeyReleased(event1);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        stage.getScene().setOnKeyPressed(event -> {
            try {
                handleKeyPressed(event);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        aiTanks.addAll(map.mapData.get(MapScr.getInstance().getId()).aiTanks);
        hearts.addAll(map.mapData.get(MapScr.getInstance().getId()).hearts);
        irons.addAll(map.mapData.get(MapScr.getInstance().getId()).irons);
        landmines.addAll(map.mapData.get(MapScr.getInstance().getId()).landmines);
        pools.addAll(map.mapData.get(MapScr.getInstance().getId()).pools);
        rocks.addAll(map.mapData.get(MapScr.getInstance().getId()).rocks);
        sheilds.addAll(map.mapData.get(MapScr.getInstance().getId()).sheilds);
        trees.addAll(map.mapData.get(MapScr.getInstance().getId()).trees);
        running = true;

        this.playerList = playerList;
        initializePlayerTanks();

        refresh.start();
        //子弹间隔线程
        if (playerTank1 != null) {
            new Thread(playerTank1).start();
        }
        if (playerTank2 != null) {
            new Thread(playerTank2).start();
        }
        if (playerTank3 != null) {
            new Thread(playerTank3).start();
        }
        if (playerTank4 != null) {
            new Thread(playerTank4).start();
        }

        scheduler.scheduleAtFixedRate(() -> {
            try {
                getGameData();
            } catch (Exception e) {
                e.printStackTrace(); // 处理或记录异常
            }
        }, 0, GAME_DATA_RECEIVE_INTERVAL_MS, TimeUnit.MILLISECONDS);
    }

    private void initializePlayerTanks() {
        // Assuming tankTypes are predefined or determined somewhere
        List<TankType> tankTypes = new ArrayList<>(List.of(TankType.values()));

        // Ensure playerList and tankTypes are properly sized
        int numberOfPlayers = playerList.size();
        int numberOfTankTypes = tankTypes.size();

        // Assign tanks to players based on playerList
        for (int i = 0; i < numberOfPlayers; i++) {
            String playerId = playerList.get(i);
            TankType tankType = tankTypes.get(i % numberOfTankTypes); // Assign type cyclically
            Tank3 tank = new Tank3();

            // Set default positions for tanks; this can be customized
            switch (i) {
                case 0 -> tank = new Tank3(0, 0, 40, 40, 2, this);
                case 1 -> tank = new Tank3(100, 100, 40, 40, 2, this);
                case 2 -> tank = new Tank3(200, 200, 40, 40, 2, this);
                case 3 -> tank = new Tank3(300, 300, 40, 40, 2, this);
                // Add more cases if you have more than 4 players
            }

            tank.setTankType(tankType);
            tank.setPlayerId(playerId);
            tank.loadImages();

            playerTanks.put(playerId, tank);
        }

        // Set the player tank references based on the map
        if (playerTanks.containsKey(playerList.get(0))) playerTank1 = playerTanks.get(playerList.get(0));
        if (playerTanks.size() > 1 && playerTanks.containsKey(playerList.get(1)))
            playerTank2 = playerTanks.get(playerList.get(1));
        if (playerTanks.size() > 2 && playerTanks.containsKey(playerList.get(2)))
            playerTank3 = playerTanks.get(playerList.get(2));
        if (playerTanks.size() > 3 && playerTanks.containsKey(playerList.get(3)))
            playerTank4 = playerTanks.get(playerList.get(3));
    }

    // 处理按键按下事件
    private void handleKeyPressed(KeyEvent event) throws Exception {
        // Adjust according to which player tank is controlled by which keys
        if(event.getCode() == KeyCode.ESCAPE){
            running = false;
            GameDlg.getInstance().Show("pause");
        }
        if(GameDlg.getInstance().isFlag()){
            running = true;
        }
        if (running) {
            if (playerTank1 != null) playerTank1.pressed(event.getCode());
            if (playerTank2 != null) playerTank2.pressed(event.getCode());
            if (playerTank3 != null) playerTank3.pressed(event.getCode());
            if (playerTank4 != null) playerTank4.pressed(event.getCode());
        }

    }

    // 处理按键松开事件
    private void handleKeyReleased(KeyEvent event) throws Exception {
        // 实现坦克停止移动的逻辑
        if (running) {
            if (playerTank1 != null) playerTank1.released(event.getCode());
            if (playerTank2 != null) playerTank2.released(event.getCode());
            if (playerTank3 != null) playerTank3.released(event.getCode());
            if (playerTank4 != null) playerTank4.released(event.getCode());
        }
    }

    // 刷新游戏界面
    private void render() throws Exception {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        // 绘制背景
        graphicsContext.drawImage(backgroundImage, 0, 0);
        graphicsContext.drawImage(backImage0,0,0);

        List<Bullet> bulletList = new ArrayList<>(bullets.values());
        // 绘制子弹
        for (int i = 0; i < bulletList.size(); i++) {
            Bullet bullet = bulletList.get(i);
            bullet.collisionBullet(bulletList);
            bullet.draw();
            bullet.move();
        }
        // 绘制玩家坦克
        if (playerTank1 != null && playerTank1.isAlive()) {
//            System.out.println("1");
            playerTank1.draw();
            playerTank1.move();
            playerTank1.collisionRocks(rocks);
            playerTank1.collisionTrees(trees);
            playerTank1.collisionBullet(bulletList);
            playerTank1.collisionPools(pools);
            playerTank1.collisionIrons(irons);
            playerTank1.collisionLandmines(landmines);
            playerTank1.collisionSheild(sheilds);
            playerTank1.collisionHeart(hearts);
            playerTank1.drawLives();
            if (playerTank2 != null) {
                playerTank1.collisionTank(playerTank2);
            }
            if (playerTank3 != null) {
                playerTank1.collisionTank(playerTank3);
            }
            if (playerTank4 != null) {
                playerTank1.collisionTank(playerTank4);
            }
        }

        if (playerTank2 != null && playerTank2.isAlive()) {
//            System.out.println("2");
            playerTank2.draw();
            playerTank2.move();
            playerTank2.collisionRocks(rocks);
            playerTank2.collisionTrees(trees);
            playerTank2.collisionBullet(bulletList);
            playerTank2.collisionPools(pools);
            playerTank2.collisionIrons(irons);
            playerTank2.collisionLandmines(landmines);
            playerTank2.collisionSheild(sheilds);
            playerTank2.collisionHeart(hearts);
            playerTank2.drawLives();
            if (playerTank1 != null) {
                playerTank2.collisionTank(playerTank1);
            }
            if (playerTank3 != null) {
                playerTank2.collisionTank(playerTank3);
            }
            if (playerTank4 != null) {
                playerTank2.collisionTank(playerTank4);
            }
        }

        if (playerTank3 != null && playerTank3.isAlive()) {
            playerTank3.draw();
            playerTank3.move();
            playerTank3.collisionRocks(rocks);
            playerTank3.collisionTrees(trees);
            playerTank3.collisionBullet(bulletList);
            playerTank3.collisionPools(pools);
            playerTank3.collisionIrons(irons);
            playerTank3.collisionLandmines(landmines);
            playerTank3.collisionSheild(sheilds);
            playerTank3.collisionHeart(hearts);
            playerTank3.drawLives();
            if (playerTank1 != null) {
                playerTank3.collisionTank(playerTank1);
            }
            if (playerTank2 != null) {
                playerTank3.collisionTank(playerTank2);
            }
            if (playerTank4 != null) {
                playerTank3.collisionTank(playerTank4);
            }
        }

        if (playerTank4 != null && playerTank4.isAlive()) {
            playerTank4.draw();
            playerTank4.move();
            playerTank4.collisionRocks(rocks);
            playerTank4.collisionTrees(trees);
            playerTank4.collisionBullet(bulletList);
            playerTank4.collisionPools(pools);
            playerTank4.collisionIrons(irons);
            playerTank4.collisionLandmines(landmines);
            playerTank4.collisionSheild(sheilds);
            playerTank4.collisionHeart(hearts);
            playerTank4.drawLives();
            if (playerTank1 != null) {
                playerTank4.collisionTank(playerTank1);
            }
            if (playerTank2 != null) {
                playerTank4.collisionTank(playerTank2);
            }
            if (playerTank3 != null) {
                playerTank4.collisionTank(playerTank3);
            }
        }
        //更新石头
        for(int i = 0; i < rocks.size(); i++){
            Rock rock = rocks.get(i);
            if(!rock.isAlive()){
                rocks.remove(i);
            }
        }
        //绘制石头
        for(int i = 0; i < rocks.size(); i++){
            Rock rock = rocks.get(i);
            rock.collisionBullet(bulletList);
            rock.draw();
        }
        //更新树丛
        for(int i = 0; i < trees.size(); i++){
            Tree tree = trees.get(i);
            tree.collisionBullet(bulletList);
            if(!tree.isAlive()){
                trees.remove(i);
            }
        }
        //绘制树丛
        for(int i = 0; i < trees.size(); i++){
            Tree tree = trees.get(i);
            tree.draw();
        }
        //更新地雷
        for (int i = 0; i < landmines.size(); i++) {
            Landmine landmine = landmines.get(i);
            if (!landmine.isAlive()) {
                landmines.remove(i);
            }
        }
        //绘制地雷
        for(int i = 0; i < landmines.size(); i++){
            Landmine landmine = landmines.get(i);
            if(!landmine.isAlive()){
                landmines.remove(i);
            }
        }
        //更新水池
        for (int i = 0; i < pools.size(); i++) {
            Pool pool = pools.get(i);
            if (!pool.isAlive()) {
                pools.remove(i);
            }
        }
        //绘制水池
        for(int i = 0; i < pools.size(); i++){
            Pool pool = pools.get(i);
            pool.draw();
        }
        //更新铁块
        for(int i = 0; i < irons.size(); i++){
            Iron iron = irons.get(i);
            iron.collisionBullet(bulletList);
            iron.draw();
        }
        //绘制铁块
        for(int i = 0; i < irons.size(); i++){
            Iron iron = irons.get(i);
            iron.collisionBullet(bulletList);
            iron.draw();
        }
        //更新桃心
        for (int i = 0; i < hearts.size(); i++) {
            Heart heart = hearts.get(i);
            if (!heart.isAlive()) {
                hearts.remove(i);
            }
        }
        //绘制桃心
        for(int i = 0; i < hearts.size(); i++){
            Heart heart = hearts.get(i);
            heart.draw();
        }
        //更新盾牌
        for (int i = 0; i < sheilds.size(); i++) {
            Sheild sheild = sheilds.get(i);
            if (!sheild.isAlive()) {
                sheilds.remove(i);
            }
        }
        //绘制盾牌
        for (int i=0;i<sheilds.size();i++) {
            Sheild sheild = sheilds.get(i);
            sheild.draw();
            if (playerTank1 != null) {
                if (playerTank1.checkCollision(sheild) && playerTank1.isInvincible()) {
                    sheild.draw(playerTank1);
                }
            }
            if (playerTank2 != null) {
                if (playerTank2.checkCollision(sheild) && playerTank2.isInvincible()) {
                    sheild.draw(playerTank2);
                }
            }
            if (playerTank3 != null) {
                if (playerTank3.checkCollision(sheild) && playerTank3.isInvincible()) {
                    sheild.draw(playerTank3);
                }
            }
            if (playerTank4 != null) {
                if (playerTank4.checkCollision(sheild) && playerTank4.isInvincible()) {
                    sheild.draw(playerTank3);
                }
            }
        }
        //产生爆炸
        for (int i = 0; i < explodes.size(); i++) {
            Explode e = explodes.get(i);
            e.draw(graphicsContext);
        }
    }

    // 刷新任务（类似游戏主循环）
    private final AnimationTimer refresh = new AnimationTimer() {
        @Override
        public void handle(long now) {
            if (running) {
                try {
                    render();  // 每一帧都调用 render() 以刷新游戏界面
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - lastSendTime >= GAME_DATA_SEND_INTERVAL_MS) {
                        sendGameData();
                        lastSendTime = currentTime;
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    };

    // 获取 GraphicsContext 对象
    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }

    // 设置 GraphicsContext 对象
    public void setGraphicsContext(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    public void stop() {
        running = false;
        executor.shutdown();  // 关闭线程池
        scheduler.shutdown();
    }

}

