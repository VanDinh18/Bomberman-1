package uet.oop.bomberman.entities.character;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.Keyboard;
import uet.oop.bomberman.level.Coordinates;

import java.util.Iterator;
import java.util.List;

import static uet.oop.bomberman.BombermanGame.game_over;
import static uet.oop.bomberman.BombermanGame.theme;

public class Bomber extends Character {

    private List<Bomb> _bombs;
    protected Keyboard _input;

    /**
     * nếu giá trị này < 0 thì cho phép đặt đối tượng Bomb tiếp theo,
     * cứ mỗi lần đặt 1 Bomb mới, giá trị này sẽ được reset về 0 và giảm dần trong mỗi lần update()
     */

    protected final double MAX_STEPS = Game.TILES_SIZE / Game.getBomberSpeed();
    protected final double rest = (MAX_STEPS - (int) MAX_STEPS) / MAX_STEPS;
    protected double _steps = 2*MAX_STEPS;
    protected int _timeBetweenPutBombs = 0;

    public Bomber(int x, int y, Board board) {
        super(x, y, board);
        _bombs = _board.getBombs();
        _input = _board.getInput();
        _sprite = Sprite.player_right;
    }

    @Override
    public void update() {
        clearBombs();
        if (!_alive) {
            afterKill();
            return;
        }

        if (_timeBetweenPutBombs < -7500) _timeBetweenPutBombs = 0;
        else _timeBetweenPutBombs--;

        animate();

//        calculateMove();
//        detectPlaceBomb();

        AIcalculateMove();
        AIdetectPlaceBomb();
    }

    @Override
    public void render(Screen screen) {
        calculateXOffset();

        if (_alive)
            chooseSprite();
        else
            _sprite = Sprite.player_dead1;

        screen.renderEntity((int) _x, (int) _y - _sprite.SIZE, this);
    }

    public void calculateXOffset() {
        int xScroll = Screen.calculateXOffset(_board, this);
        Screen.setOffset(xScroll, 0);
    }
    void AIdetectPlaceBomb() {
        int x = Coordinates.pixelToTile(_x + _sprite.getSize()/2);
        int y = Coordinates.pixelToTile(_y - _sprite.getSize()/2 );
        if(!Game.getBoard().detectNoEnemies() && Game.getBombRate()>0 && _timeBetweenPutBombs < 0){
            for(int i = 1 ; i < 2 + Game.getBombRadius() ; i++){
                Character character = _board.getCharacterAtExcluding(this.getXTile()+i, this.getYTile(),this);
                Entity a = _board.getEntityAt(this.getXTile()+1,this.getYTile());
                if(character!=null) {
                    placeBomb(x, y);
                    Game.addBombRate(-1);
                    _timeBetweenPutBombs = 10;
                }
                else if(a instanceof LayeredEntity){
                    if(((LayeredEntity) a).getTopEntity() instanceof Brick){
                        placeBomb(x, y);
                        Game.addBombRate(-1);
                        _timeBetweenPutBombs = 10;
                    }
                }
            }
            for(int i = 1 ; i < 2 + Game.getBombRadius() ; i++){
                Character character = _board.getCharacterAtExcluding(this.getXTile()-i, this.getYTile(),this);
                Entity a = _board.getEntityAt(this.getXTile()-1,this.getYTile());
                if(character!=null) {
                    placeBomb(x, y);
                    Game.addBombRate(-1);
                    _timeBetweenPutBombs = 10;
                }
                else if(a instanceof LayeredEntity){
                    if(((LayeredEntity) a).getTopEntity() instanceof Brick){
                        placeBomb(x, y);
                        Game.addBombRate(-1);
                        _timeBetweenPutBombs = 10;
                    }
                }
            }
            for(int i = 1 ; i < 2 + Game.getBombRadius() ; i++){
                Character character = _board.getCharacterAtExcluding(this.getXTile(), this.getYTile()+i,this);
                Entity a = _board.getEntityAt(this.getXTile(),this.getYTile()+1);
                if(character!=null) {
                    placeBomb(x, y);
                    Game.addBombRate(-1);
                    _timeBetweenPutBombs = 10;
                }
                else if(a instanceof LayeredEntity){
                    if(((LayeredEntity) a).getTopEntity() instanceof Brick){
                        placeBomb(x, y);
                        Game.addBombRate(-1);
                        _timeBetweenPutBombs = 10;
                    }
                }
            }
            for(int i = 1 ; i < 2 + Game.getBombRadius() ; i++){
                Character character = _board.getCharacterAtExcluding(this.getXTile(), this.getYTile() -i,this);
                Entity a = _board.getEntityAt(this.getXTile(),this.getYTile()-1);
                if(character!=null) {
                    placeBomb(x, y);
                    Game.addBombRate(-1);
                    _timeBetweenPutBombs = 10;
                }
                else if(a instanceof LayeredEntity){
                    if(((LayeredEntity) a).getTopEntity() instanceof Brick){
                        placeBomb(x, y);
                        Game.addBombRate(-1);
                        _timeBetweenPutBombs = 10;
                    }
                }
            }

        }
    }
    /**
     * Kiểm tra xem có đặt được bom hay không? nếu có thì đặt bom tại vị trí hiện tại của Bomber
     */
    private void detectPlaceBomb() {
        // TODO: kiểm tra xem phím điều khiển đặt bom có được gõ và giá trị _timeBetweenPutBombs, Game.getBombRate() có thỏa mãn hay không
        if(_input.space && Game.getBombRate()>0 && _timeBetweenPutBombs < 0){
            int x = Coordinates.pixelToTile(_x + _sprite.getSize()/2);
            int y = Coordinates.pixelToTile(_y - _sprite.getSize()/2 );

            placeBomb(x,y);
            Game.addBombRate(-1);
            _timeBetweenPutBombs = 30;
        }
        // TODO:  Game.getBombRate() sẽ trả về số lượng bom có thể đặt liên tiếp tại thời điểm hiện tại
        // TODO: _timeBetweenPutBombs dùng để ngăn chặn Bomber đặt 2 Bomb cùng tại 1 vị trí trong 1 khoảng thời gian quá ngắn
        // TODO: nếu 3 điều kiện trên thỏa mãn thì thực hiện đặt bom bằng placeBomb()
        // TODO: sau khi đặt, nhớ giảm số lượng Bomb Rate và reset _timeBetweenPutBombs về 0
    }

    protected void placeBomb(int x, int y) {
        // TODO: thực hiện tạo đối tượng bom, đặt vào vị trí (x, y)
        Bomb bomb = new Bomb(x,y,_board);
        _board.addBomb(bomb);

    }

    private void clearBombs() {
        Iterator<Bomb> bs = _bombs.iterator();

        Bomb b;
        while (bs.hasNext()) {
            b = bs.next();
            if (b.isRemoved()) {
                bs.remove();
                Game.addBombRate(1);
            }
        }

    }

    @Override
    public void kill() {
        if (!_alive) return;
        _alive = false;
    }

    @Override
    protected void afterKill() {
        if (_timeAfter > 0) --_timeAfter;
        else {
            _board.endGame();
        }
    }
    void AIcalculateMove(){
        double xa = 0, ya = 0;
        if(_steps <= 0){
            _direction = BomberAI.calculateDirection(_board.getBombs(),_board.getBomber());
            System.out.println(_direction);
            _steps = 2*MAX_STEPS;
        }
        if(_direction == 0) ya--;
        if(_direction == 2) ya++;
        if(_direction == 3) xa--;
        if(_direction == 1) xa++;
        // TODO: sử dụng canMove() để kiểm tra xem có thể di chuyển tới điểm đã tính toán hay không

        // TODO: sử dụng move() để di chuyển
        if(canMove(xa, ya)) {
            _steps -= 1 + rest;
            move(xa * Game.getBomberSpeed(), ya * Game.getBomberSpeed());
            _moving = true;
        } else {
            _steps = 0;
            _moving = false;
        }
    }
    @Override
    protected void calculateMove() {
        // TODO: xử lý nhận tín hiệu điều khiển hướng đi từ _input và gọi move() để thực hiện di chuyển
        double xa = 0 , ya = 0 ;
        if(_input.up) ya--;
        if(_input.down) ya++;
        if(_input.left) xa--;
        if(_input.right) xa++;

        // TODO: nhớ cập nhật lại giá trị cờ _moving khi thay đổi trạng thái di chuyển
        if(xa != 0 || ya != 0)  {
            move(xa * Game.getBomberSpeed(), ya * Game.getBomberSpeed());
            _moving = true;
        } else {
            _moving = false;
        }
    }

    @Override
    public boolean canMove(double x, double y) {
        // TODO: kiểm tra có đối tượng tại vị trí chuẩn bị di chuyển đến và có thể di chuyển tới đó hay không
        for(int c = 0; c < 4 ; c++){
            double xt = ((_x + x) + c % 2 * 11) / Game.TILES_SIZE;
            double yt = ((_y + y) + c / 2 * 12 - 13) / Game.TILES_SIZE;
            /*
            x  ::  y-13
            x+11  ::  y-13
            x  ::  y-1
            x+11  ::  y-1
             */

            Entity a = _board.getEntity(xt,yt,this);
            if(!a.collide(this))
                return false;
        }
        return true;
    }

    @Override
    public void move(double xa, double ya) {

        // TODO: sử dụng canMove() để kiểm tra xem có thể di chuyển tới điểm đã tính toán hay không và thực hiện thay đổi tọa độ _x, _y
        if(canMove(xa, ya)) {
            _y += ya;
            _x += xa;
        }
        // TODO: nhớ cập nhật giá trị _direction sau khi di chuyển
        if(xa > 0) _direction = 1;
        if(xa < 0) _direction = 3;
        if(ya > 0) _direction = 2;
        if(ya < 0) _direction = 0;
    }

    @Override
    public boolean collide(Entity e) {
        // TODO: xử lý va chạm với Flame
        if( e instanceof Flame){
            kill();
            return false;
        }
        // TODO: xử lý va chạm với Enemy
        if( e instanceof Enemy){
            kill();
            return true;
        }
        return true;
    }

    private void chooseSprite() {
        switch (_direction) {
            case 0:
                _sprite = Sprite.player_up;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, _animate, 20);
                }
                break;
            case 1:
                _sprite = Sprite.player_right;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
            case 2:
                _sprite = Sprite.player_down;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, _animate, 20);
                }
                break;
            case 3:
                _sprite = Sprite.player_left;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, _animate, 20);
                }
                break;
            default:
                _sprite = Sprite.player_right;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
        }
    }
}
