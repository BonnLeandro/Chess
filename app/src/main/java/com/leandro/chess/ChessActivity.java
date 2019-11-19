package com.leandro.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveGenerator;
import com.github.bhlangonijr.chesslib.move.MoveGeneratorException;
import com.github.bhlangonijr.chesslib.move.MoveList;
import com.jwang123.flagkit.FlagKit;

public class ChessActivity extends AppCompatActivity
{
    // interfaz
    private TextView TextView_movements;        // pendiente
    private TextView TextView_oponent_name;
    private TextView TextView_oponent_elo;
    private TextView TextView_oponent_timer;
    private TextView TextView_profile_name;
    private TextView TextView_profile_elo;
    private TextView TextView_profile_timer;
    private ImageView ImageView_oponent_image;
    private ImageView ImageView_oponent_flag;
    private ImageView ImageView_profile_image;
    private ImageView ImageView_profile_flag;
    private Button Button_draw;
    private Button Button_surrender;
    private ImageView ImageView_pawn_queen;
    private ImageView ImageView_pawn_rook;
    private ImageView ImageView_pawn_bishop;
    private ImageView ImageView_pawn_knight;
    private LinearLayout LinearLayout_pawn_chioces;

    // tablero
    private Board board = new Board();

    private Square square_to;
    private Square square_from;
    private MoveList possible_moves;

    private TextView[][] DisplayBoard = new TextView[8][8];
    private TextView[][] DisplayBoardBackground = new TextView[8][8];

    private Boolean piece_selected = false;
    private Boolean isPawnSelectingPiece = false;
    private Side my_color;

    private static final int SQUARES_QUANTITY = 64;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // vinculo todos los objetos
        TextView_movements = (TextView) findViewById(R.id.TextView_movements);
        TextView_oponent_name = (TextView) findViewById(R.id.TextView_oponent_name);
        TextView_oponent_elo = (TextView) findViewById(R.id.TextView_oponent_elo);
        TextView_oponent_timer = (TextView) findViewById(R.id.TextView_oponent_timer);
        TextView_profile_name = (TextView) findViewById(R.id.TextView_profile_name);
        TextView_profile_elo = (TextView) findViewById(R.id.TextView_profile_elo);
        TextView_profile_timer = (TextView) findViewById(R.id.TextView_profile_timer);

        ImageView_oponent_image = (ImageView) findViewById(R.id.ImageView_oponent_image);
        ImageView_oponent_flag = (ImageView) findViewById(R.id.ImageView_oponent_flag);
        ImageView_profile_image = (ImageView) findViewById(R.id.ImageView_profile_image);
        ImageView_profile_flag = (ImageView) findViewById(R.id.ImageView_profile_flag);
        ImageView_pawn_queen = (ImageView) findViewById(R.id.ImageView_pawn_queen);
        ImageView_pawn_rook = (ImageView) findViewById(R.id.ImageView_pawn_rook);
        ImageView_pawn_bishop = (ImageView) findViewById(R.id.ImageView_pawn_bishop);
        ImageView_pawn_knight = (ImageView) findViewById(R.id.ImageView_pawn_knight);

        LinearLayout_pawn_chioces = (LinearLayout) findViewById(R.id.LinearLayout_pawn_chioces);

        Button_draw = (Button) findViewById(R.id.Button_draw);
        Button_surrender = (Button) findViewById(R.id.Button_surrender);




        // valores que hay que pasar (AGREGAR DESDE LA LLAMADA)
        TextView_oponent_name.setText("leandro ariel bonn");
        TextView_oponent_elo.setText( "1210" );
        TextView_oponent_timer.setText("10:51" );
        TextView_profile_name.setText( "franco kenji" );
        TextView_profile_elo.setText( "1210" );
        TextView_profile_timer.setText( "12:24" );

        ImageView_profile_image.setImageDrawable(getDrawable(R.drawable.bking));
        ImageView_oponent_image.setImageDrawable(getDrawable(R.drawable.wking));

        my_color = Side.WHITE;

        /*
        Librería de iconos de banderas: https://github.com/WANGjieJacques/flagkit
         */
        ImageView_profile_flag.setImageDrawable(FlagKit.drawableWithFlag(ChessActivity.this, "ar"));
        ImageView_oponent_flag.setImageDrawable(FlagKit.drawableWithFlag(ChessActivity.this, "ar"));




        initializeBoard();
    }




    private void initializeBoard()
    {
        DisplayBoard[7][0]              = (TextView) findViewById(R.id.R00);
        DisplayBoardBackground[7][0]    = (TextView) findViewById(R.id.R000);
        DisplayBoard[7][1]              = (TextView) findViewById(R.id.R01);
        DisplayBoardBackground[7][1]    = (TextView) findViewById(R.id.R001);
        DisplayBoard[7][2]              = (TextView) findViewById(R.id.R02);
        DisplayBoardBackground[7][2]    = (TextView) findViewById(R.id.R002);
        DisplayBoard[7][3]              = (TextView) findViewById(R.id.R03);
        DisplayBoardBackground[7][3]    = (TextView) findViewById(R.id.R003);
        DisplayBoard[7][4]              = (TextView) findViewById(R.id.R04);
        DisplayBoardBackground[7][4]    = (TextView) findViewById(R.id.R004);
        DisplayBoard[7][5]              = (TextView) findViewById(R.id.R05);
        DisplayBoardBackground[7][5]    = (TextView) findViewById(R.id.R005);
        DisplayBoard[7][6]              = (TextView) findViewById(R.id.R06);
        DisplayBoardBackground[7][6]    = (TextView) findViewById(R.id.R006);
        DisplayBoard[7][7]              = (TextView) findViewById(R.id.R07);
        DisplayBoardBackground[7][7]    = (TextView) findViewById(R.id.R007);

        DisplayBoard[6][0]              = (TextView) findViewById(R.id.R10);
        DisplayBoardBackground[6][0]    = (TextView) findViewById(R.id.R010);
        DisplayBoard[6][1]              = (TextView) findViewById(R.id.R11);
        DisplayBoardBackground[6][1]    = (TextView) findViewById(R.id.R011);
        DisplayBoard[6][2]              = (TextView) findViewById(R.id.R12);
        DisplayBoardBackground[6][2]    = (TextView) findViewById(R.id.R012);
        DisplayBoard[6][3]              = (TextView) findViewById(R.id.R13);
        DisplayBoardBackground[6][3]    = (TextView) findViewById(R.id.R013);
        DisplayBoard[6][4]              = (TextView) findViewById(R.id.R14);
        DisplayBoardBackground[6][4]    = (TextView) findViewById(R.id.R014);
        DisplayBoard[6][5]              = (TextView) findViewById(R.id.R15);
        DisplayBoardBackground[6][5]    = (TextView) findViewById(R.id.R015);
        DisplayBoard[6][6]              = (TextView) findViewById(R.id.R16);
        DisplayBoardBackground[6][6]    = (TextView) findViewById(R.id.R016);
        DisplayBoard[6][7]              = (TextView) findViewById(R.id.R17);
        DisplayBoardBackground[6][7]    = (TextView) findViewById(R.id.R017);

        DisplayBoard[5][0]              = (TextView) findViewById(R.id.R20);
        DisplayBoardBackground[5][0]    = (TextView) findViewById(R.id.R020);
        DisplayBoard[5][1]              = (TextView) findViewById(R.id.R21);
        DisplayBoardBackground[5][1]    = (TextView) findViewById(R.id.R021);
        DisplayBoard[5][2]              = (TextView) findViewById(R.id.R22);
        DisplayBoardBackground[5][2]    = (TextView) findViewById(R.id.R022);
        DisplayBoard[5][3]              = (TextView) findViewById(R.id.R23);
        DisplayBoardBackground[5][3]    = (TextView) findViewById(R.id.R023);
        DisplayBoard[5][4]              = (TextView) findViewById(R.id.R24);
        DisplayBoardBackground[5][4]    = (TextView) findViewById(R.id.R024);
        DisplayBoard[5][5]              = (TextView) findViewById(R.id.R25);
        DisplayBoardBackground[5][5]    = (TextView) findViewById(R.id.R025);
        DisplayBoard[5][6]              = (TextView) findViewById(R.id.R26);
        DisplayBoardBackground[5][6]    = (TextView) findViewById(R.id.R026);
        DisplayBoard[5][7]              = (TextView) findViewById(R.id.R27);
        DisplayBoardBackground[5][7]    = (TextView) findViewById(R.id.R027);

        DisplayBoard[4][0]              = (TextView) findViewById(R.id.R30);
        DisplayBoardBackground[4][0]    = (TextView) findViewById(R.id.R030);
        DisplayBoard[4][1]              = (TextView) findViewById(R.id.R31);
        DisplayBoardBackground[4][1]    = (TextView) findViewById(R.id.R031);
        DisplayBoard[4][2]              = (TextView) findViewById(R.id.R32);
        DisplayBoardBackground[4][2]    = (TextView) findViewById(R.id.R032);
        DisplayBoard[4][3]              = (TextView) findViewById(R.id.R33);
        DisplayBoardBackground[4][3]    = (TextView) findViewById(R.id.R033);
        DisplayBoard[4][4]              = (TextView) findViewById(R.id.R34);
        DisplayBoardBackground[4][4]    = (TextView) findViewById(R.id.R034);
        DisplayBoard[4][5]              = (TextView) findViewById(R.id.R35);
        DisplayBoardBackground[4][5]    = (TextView) findViewById(R.id.R035);
        DisplayBoard[4][6]              = (TextView) findViewById(R.id.R36);
        DisplayBoardBackground[4][6]    = (TextView) findViewById(R.id.R036);
        DisplayBoard[4][7]              = (TextView) findViewById(R.id.R37);
        DisplayBoardBackground[4][7]    = (TextView) findViewById(R.id.R037);

        DisplayBoard[3][0]              = (TextView) findViewById(R.id.R40);
        DisplayBoardBackground[3][0]    = (TextView) findViewById(R.id.R040);
        DisplayBoard[3][1]              = (TextView) findViewById(R.id.R41);
        DisplayBoardBackground[3][1]    = (TextView) findViewById(R.id.R041);
        DisplayBoard[3][2]              = (TextView) findViewById(R.id.R42);
        DisplayBoardBackground[3][2]    = (TextView) findViewById(R.id.R042);
        DisplayBoard[3][3]              = (TextView) findViewById(R.id.R43);
        DisplayBoardBackground[3][3]    = (TextView) findViewById(R.id.R043);
        DisplayBoard[3][4]              = (TextView) findViewById(R.id.R44);
        DisplayBoardBackground[3][4]    = (TextView) findViewById(R.id.R044);
        DisplayBoard[3][5]              = (TextView) findViewById(R.id.R45);
        DisplayBoardBackground[3][5]    = (TextView) findViewById(R.id.R045);
        DisplayBoard[3][6]              = (TextView) findViewById(R.id.R46);
        DisplayBoardBackground[3][6]    = (TextView) findViewById(R.id.R046);
        DisplayBoard[3][7]              = (TextView) findViewById(R.id.R47);
        DisplayBoardBackground[3][7]    = (TextView) findViewById(R.id.R047);

        DisplayBoard[2][0]              = (TextView) findViewById(R.id.R50);
        DisplayBoardBackground[2][0]    = (TextView) findViewById(R.id.R050);
        DisplayBoard[2][1]              = (TextView) findViewById(R.id.R51);
        DisplayBoardBackground[2][1]    = (TextView) findViewById(R.id.R051);
        DisplayBoard[2][2]              = (TextView) findViewById(R.id.R52);
        DisplayBoardBackground[2][2]    = (TextView) findViewById(R.id.R052);
        DisplayBoard[2][3]              = (TextView) findViewById(R.id.R53);
        DisplayBoardBackground[2][3]    = (TextView) findViewById(R.id.R053);
        DisplayBoard[2][4]              = (TextView) findViewById(R.id.R54);
        DisplayBoardBackground[2][4]    = (TextView) findViewById(R.id.R054);
        DisplayBoard[2][5]              = (TextView) findViewById(R.id.R55);
        DisplayBoardBackground[2][5]    = (TextView) findViewById(R.id.R055);
        DisplayBoard[2][6]              = (TextView) findViewById(R.id.R56);
        DisplayBoardBackground[2][6]    = (TextView) findViewById(R.id.R056);
        DisplayBoard[2][7]              = (TextView) findViewById(R.id.R57);
        DisplayBoardBackground[2][7]    = (TextView) findViewById(R.id.R057);

        DisplayBoard[1][0]              = (TextView) findViewById(R.id.R60);
        DisplayBoardBackground[1][0]    = (TextView) findViewById(R.id.R060);
        DisplayBoard[1][1]              = (TextView) findViewById(R.id.R61);
        DisplayBoardBackground[1][1]    = (TextView) findViewById(R.id.R061);
        DisplayBoard[1][2]              = (TextView) findViewById(R.id.R62);
        DisplayBoardBackground[1][2]    = (TextView) findViewById(R.id.R062);
        DisplayBoard[1][3]              = (TextView) findViewById(R.id.R63);
        DisplayBoardBackground[1][3]    = (TextView) findViewById(R.id.R063);
        DisplayBoard[1][4]              = (TextView) findViewById(R.id.R64);
        DisplayBoardBackground[1][4]    = (TextView) findViewById(R.id.R064);
        DisplayBoard[1][5]              = (TextView) findViewById(R.id.R65);
        DisplayBoardBackground[1][5]    = (TextView) findViewById(R.id.R065);
        DisplayBoard[1][6]              = (TextView) findViewById(R.id.R66);
        DisplayBoardBackground[1][6]    = (TextView) findViewById(R.id.R066);
        DisplayBoard[1][7]              = (TextView) findViewById(R.id.R67);
        DisplayBoardBackground[1][7]    = (TextView) findViewById(R.id.R067);

        DisplayBoard[0][0]              = (TextView) findViewById(R.id.R70);
        DisplayBoardBackground[0][0]    = (TextView) findViewById(R.id.R070);
        DisplayBoard[0][1]              = (TextView) findViewById(R.id.R71);
        DisplayBoardBackground[0][1]    = (TextView) findViewById(R.id.R071);
        DisplayBoard[0][2]              = (TextView) findViewById(R.id.R72);
        DisplayBoardBackground[0][2]    = (TextView) findViewById(R.id.R072);
        DisplayBoard[0][3]              = (TextView) findViewById(R.id.R73);
        DisplayBoardBackground[0][3]    = (TextView) findViewById(R.id.R073);
        DisplayBoard[0][4]              = (TextView) findViewById(R.id.R74);
        DisplayBoardBackground[0][4]    = (TextView) findViewById(R.id.R074);
        DisplayBoard[0][5]              = (TextView) findViewById(R.id.R75);
        DisplayBoardBackground[0][5]    = (TextView) findViewById(R.id.R075);
        DisplayBoard[0][6]              = (TextView) findViewById(R.id.R76);
        DisplayBoardBackground[0][6]    = (TextView) findViewById(R.id.R076);
        DisplayBoard[0][7]              = (TextView) findViewById(R.id.R77);
        DisplayBoardBackground[0][7]    = (TextView) findViewById(R.id.R077);

        setBoard();
    }




    private void setBoard()
    {
        // imprime en pantalla las piezas almacenadas en "Board"
        Piece p;




        ///////////////////////////////////////////////////// SOLO PARA TESTEO ( ELIMINAR )
        my_color = board.getSideToMove();
        /////////////////////////////////////////////////////




        for (int row = 0; row < 8; row++)
        {
            for (int col = 0; col < 8; col++)
            {
                // obtengo la pieza y la muestro
                if (my_color == Side.WHITE)     p = board.getPiece(Square.squareAt(8 * row + col));
                else                            p = board.getPiece(Square.squareAt(SQUARES_QUANTITY - 1 - 8 * row - col));

                if (p == Piece.NONE) DisplayBoard[row][col].setBackgroundResource(0);

                else if (p == Piece.BLACK_PAWN)
                    DisplayBoard[row][col].setBackgroundResource(R.drawable.bpawn);
                else if (p == Piece.WHITE_PAWN)
                    DisplayBoard[row][col].setBackgroundResource(R.drawable.wpawn);
                else if (p == Piece.BLACK_KNIGHT)
                    DisplayBoard[row][col].setBackgroundResource(R.drawable.bknight);
                else if (p == Piece.WHITE_KNIGHT)
                    DisplayBoard[row][col].setBackgroundResource(R.drawable.wknight);
                else if (p == Piece.BLACK_BISHOP)
                    DisplayBoard[row][col].setBackgroundResource(R.drawable.bbishop);
                else if (p == Piece.WHITE_BISHOP)
                    DisplayBoard[row][col].setBackgroundResource(R.drawable.wbishop);
                else if (p == Piece.BLACK_ROOK)
                    DisplayBoard[row][col].setBackgroundResource(R.drawable.brook);
                else if (p == Piece.WHITE_ROOK)
                    DisplayBoard[row][col].setBackgroundResource(R.drawable.wrook);
                else if (p == Piece.BLACK_KING)
                    DisplayBoard[row][col].setBackgroundResource(R.drawable.bking);
                else if (p == Piece.WHITE_KING)
                    DisplayBoard[row][col].setBackgroundResource(R.drawable.wking);
                else if (p == Piece.BLACK_QUEEN)
                    DisplayBoard[row][col].setBackgroundResource(R.drawable.bqueen);
                else if (p == Piece.WHITE_QUEEN)
                    DisplayBoard[row][col].setBackgroundResource(R.drawable.wqueen);
            }
        }

        kingCheck();
    }




    public void onClick(View v)
    {
        // si estoy eligiendo el cambio de pieza con el peón, ignoro al tablero
        if (isPawnSelectingPiece)           return;

        // lectura de los clicks de las casillas y procesamiento
        my_color = board.getSideToMove();

        switch (v.getId())
        {
            case R.id.R00:
                if (my_color == Side.WHITE)     square_to = Square.A8;
                else                            square_to = Square.H1;
                break;
            case R.id.R01:
                if (my_color == Side.WHITE)     square_to = Square.B8;
                else                            square_to = Square.G1;
                break;
            case R.id.R02:
                if (my_color == Side.WHITE)     square_to = Square.C8;
                else                            square_to = Square.F1;
                break;
            case R.id.R03:
                if (my_color == Side.WHITE)     square_to = Square.D8;
                else                            square_to = Square.E1;
                break;
            case R.id.R04:
                if (my_color == Side.WHITE)     square_to = Square.E8;
                else                            square_to = Square.D1;
                break;
            case R.id.R05:
                if (my_color == Side.WHITE)     square_to = Square.F8;
                else                            square_to = Square.C1;
                break;
            case R.id.R06:
                if (my_color == Side.WHITE)     square_to = Square.G8;
                else                            square_to = Square.B1;
                break;
            case R.id.R07:
                if (my_color == Side.WHITE)     square_to = Square.H8;
                else                            square_to = Square.A1;
                break;

            case R.id.R10:
                if (my_color == Side.WHITE)     square_to = Square.A7;
                else                            square_to = Square.H2;
                break;
            case R.id.R11:
                if (my_color == Side.WHITE)     square_to = Square.B7;
                else                            square_to = Square.G2;
                break;
            case R.id.R12:
                if (my_color == Side.WHITE)     square_to = Square.C7;
                else                            square_to = Square.F2;
                break;
            case R.id.R13:
                if (my_color == Side.WHITE)     square_to = Square.D7;
                else                            square_to = Square.E2;
                break;
            case R.id.R14:
                if (my_color == Side.WHITE)     square_to = Square.E7;
                else                            square_to = Square.D2;
                break;
            case R.id.R15:
                if (my_color == Side.WHITE)     square_to = Square.F7;
                else                            square_to = Square.C2;
                break;
            case R.id.R16:
                if (my_color == Side.WHITE)     square_to = Square.G7;
                else                            square_to = Square.B2;
                break;
            case R.id.R17:
                if (my_color == Side.WHITE)     square_to = Square.H7;
                else                            square_to = Square.A2;
                break;

            case R.id.R20:
                if (my_color == Side.WHITE)     square_to = Square.A6;
                else                            square_to = Square.H3;
                break;
            case R.id.R21:
                if (my_color == Side.WHITE)     square_to = Square.B6;
                else                            square_to = Square.G3;
                break;
            case R.id.R22:
                if (my_color == Side.WHITE)     square_to = Square.C6;
                else                            square_to = Square.F3;
                break;
            case R.id.R23:
                if (my_color == Side.WHITE)     square_to = Square.D6;
                else                            square_to = Square.E3;
                break;
            case R.id.R24:
                if (my_color == Side.WHITE)     square_to = Square.E6;
                else                            square_to = Square.D3;
                break;
            case R.id.R25:
                if (my_color == Side.WHITE)     square_to = Square.F6;
                else                            square_to = Square.C3;
                break;
            case R.id.R26:
                if (my_color == Side.WHITE)     square_to = Square.G6;
                else                            square_to = Square.B3;
                break;
            case R.id.R27:
                if (my_color == Side.WHITE)     square_to = Square.H6;
                else                            square_to = Square.A3;
                break;

            case R.id.R30:
                if (my_color == Side.WHITE)     square_to = Square.A5;
                else                            square_to = Square.H4;
                break;
            case R.id.R31:
                if (my_color == Side.WHITE)     square_to = Square.B5;
                else                            square_to = Square.G4;
                break;
            case R.id.R32:
                if (my_color == Side.WHITE)     square_to = Square.C5;
                else                            square_to = Square.F4;
                break;
            case R.id.R33:
                if (my_color == Side.WHITE)     square_to = Square.D5;
                else                            square_to = Square.E4;
                break;
            case R.id.R34:
                if (my_color == Side.WHITE)     square_to = Square.E5;
                else                            square_to = Square.D4;
                break;
            case R.id.R35:
                if (my_color == Side.WHITE)     square_to = Square.F5;
                else                            square_to = Square.C4;
                break;
            case R.id.R36:
                if (my_color == Side.WHITE)     square_to = Square.G5;
                else                            square_to = Square.B4;
                break;
            case R.id.R37:
                if (my_color == Side.WHITE)     square_to = Square.H5;
                else                            square_to = Square.A4;
                break;

            case R.id.R40:
                if (my_color == Side.WHITE)     square_to = Square.A4;
                else                            square_to = Square.H5;
                break;
            case R.id.R41:
                if (my_color == Side.WHITE)     square_to = Square.B4;
                else                            square_to = Square.G5;
                break;
            case R.id.R42:
                if (my_color == Side.WHITE)     square_to = Square.C4;
                else                            square_to = Square.F5;
                break;
            case R.id.R43:
                if (my_color == Side.WHITE)     square_to = Square.D4;
                else                            square_to = Square.E5;
                break;
            case R.id.R44:
                if (my_color == Side.WHITE)     square_to = Square.E4;
                else                            square_to = Square.D5;
                break;
            case R.id.R45:
                if (my_color == Side.WHITE)     square_to = Square.F4;
                else                            square_to = Square.C5;
                break;
            case R.id.R46:
                if (my_color == Side.WHITE)     square_to = Square.G4;
                else                            square_to = Square.B5;
                break;
            case R.id.R47:
                if (my_color == Side.WHITE)     square_to = Square.H4;
                else                            square_to = Square.A5;
                break;

            case R.id.R50:
                if (my_color == Side.WHITE)     square_to = Square.A3;
                else                            square_to = Square.H6;
                break;
            case R.id.R51:
                if (my_color == Side.WHITE)     square_to = Square.B3;
                else                            square_to = Square.G6;
                break;
            case R.id.R52:
                if (my_color == Side.WHITE)     square_to = Square.C3;
                else                            square_to = Square.F6;
                break;
            case R.id.R53:
                if (my_color == Side.WHITE)     square_to = Square.D3;
                else                            square_to = Square.E6;
                break;
            case R.id.R54:
                if (my_color == Side.WHITE)     square_to = Square.E3;
                else                            square_to = Square.D6;
                break;
            case R.id.R55:
                if (my_color == Side.WHITE)     square_to = Square.F3;
                else                            square_to = Square.C6;
                break;
            case R.id.R56:
                if (my_color == Side.WHITE)     square_to = Square.G3;
                else                            square_to = Square.B6;
                break;
            case R.id.R57:
                if (my_color == Side.WHITE)     square_to = Square.H3;
                else                            square_to = Square.A6;
                break;

            case R.id.R60:
                if (my_color == Side.WHITE)     square_to = Square.A2;
                else                            square_to = Square.H7;
                break;
            case R.id.R61:
                if (my_color == Side.WHITE)     square_to = Square.B2;
                else                            square_to = Square.G7;
                break;
            case R.id.R62:
                if (my_color == Side.WHITE)     square_to = Square.C2;
                else                            square_to = Square.F7;
                break;
            case R.id.R63:
                if (my_color == Side.WHITE)     square_to = Square.D2;
                else                            square_to = Square.E7;
                break;
            case R.id.R64:
                if (my_color == Side.WHITE)     square_to = Square.E2;
                else                            square_to = Square.D7;
                break;
            case R.id.R65:
                if (my_color == Side.WHITE)     square_to = Square.F2;
                else                            square_to = Square.C7;
                break;
            case R.id.R66:
                if (my_color == Side.WHITE)     square_to = Square.G2;
                else                            square_to = Square.B7;
                break;
            case R.id.R67:
                if (my_color == Side.WHITE)     square_to = Square.H2;
                else                            square_to = Square.A7;
                break;

            case R.id.R70:
                if (my_color == Side.WHITE)     square_to = Square.A1;
                else                            square_to = Square.H8;
                break;
            case R.id.R71:
                if (my_color == Side.WHITE)     square_to = Square.B1;
                else                            square_to = Square.G8;
                break;
            case R.id.R72:
                if (my_color == Side.WHITE)     square_to = Square.C1;
                else                            square_to = Square.F8;
                break;
            case R.id.R73:
                if (my_color == Side.WHITE)     square_to = Square.D1;
                else                            square_to = Square.E8;
                break;
            case R.id.R74:
                if (my_color == Side.WHITE)     square_to = Square.E1;
                else                            square_to = Square.D8;
                break;
            case R.id.R75:
                if (my_color == Side.WHITE)     square_to = Square.F1;
                else                            square_to = Square.C8;
                break;
            case R.id.R76:
                if (my_color == Side.WHITE)     square_to = Square.G1;
                else                            square_to = Square.B8;
                break;
            case R.id.R77:
                if (my_color == Side.WHITE)     square_to = Square.H1;
                else                            square_to = Square.A8;
                break;
        }


        if (!piece_selected)      // si no había nada seleccionado antes
        {
            analizeNewSelection();
        }
        else                        // si ya había seleccionado antes una pieza
        {
            // dejo de mostrar los movimientos posibles y saco la seleccion de la pieza
            piece_selected = false;


            // veo si la nueva seleccion es uno de los movimientos posibles
            for (int i = 0 ; i < possible_moves.size() ; i++ )
            {
                if (possible_moves.get(i).getTo() == square_to)     // si la casilla seleccionada es válida, hago el movimiento
                {
                    // si el movimiento no es por un peón que llego al final, lo hago
                    // el movimiento del peón con cambio de pieza  se hace en pawnChoice() con la ventana al usuario
                    if ( ! isPawnFinish() )
                    {
                        resetColor();
                        board.doMove(new Move(square_from,square_to));
                        setBoard();
                    }

                    return;
                }
            }


            // si llegue aca, la nueva seleccion no es un movimiento de la seleccion anterior
            // lo analizo como un movimiento nuevo
            analizeNewSelection();
        }
    }




    private void analizeNewSelection()
    {
        // si la seleccion es un lugar vacío o una pieza del oponente, me voy
        if (board.getPiece(square_to) == Piece.NONE)                return;
        if (board.getPiece(square_to).getPieceSide() != my_color)   return;


        square_from = square_to;
        resetColor();

        if (my_color == Side.WHITE)
        {
            DisplayBoardBackground[square_from.ordinal()/8][square_from.ordinal()%8].setBackgroundResource(R.color.colorSelected);
        }
        else
        {
            DisplayBoardBackground[7-square_from.ordinal()/8][7-square_from.ordinal()%8].setBackgroundResource(R.color.colorSelected);
        }

        // obtengo los movimientos posibles para la pieza seleccionada
        getPossibleMoves();

        // si hay algun movimiento válido, lo muestro y dejo la seleccion de la pieza para la proxima
        if (possible_moves.size() > 0)
        {
            setColorAtAllowedPosition();
            piece_selected = true;
        }

        // veo si el rey esta en jaque, para mostrarlo
        kingCheck();
    }




    private void getPossibleMoves ()
    {
        // obtengo todos los posibles movimientos del tablero
        try
        {
            possible_moves = MoveGenerator.generateLegalMoves(board);
        }
        catch (MoveGeneratorException e)
        {
            e.printStackTrace();
            return;
        }

        // filtro los que no son de mi pieza
        for (int i = 0 ; i < possible_moves.size() ; i++ )
        {
            if (possible_moves.get(i).getFrom() != square_from)
            {
                possible_moves.remove(i);
                i--;
            }
        }
    }




    private void kingCheck()
    {
        Square king_attacked_square;

        my_color = board.getSideToMove();

        if ( board.isKingAttacked() )
        {
            king_attacked_square = board.getKingSquare(my_color);

            if (my_color == Side.WHITE)
            {
                DisplayBoardBackground[king_attacked_square.ordinal()/8][king_attacked_square.ordinal()%8].setBackgroundResource(R.color.colorKingInDanger);
            }
            else
            {
                DisplayBoardBackground[7-king_attacked_square.ordinal()/8][7-king_attacked_square.ordinal()%8].setBackgroundResource(R.color.colorKingInDanger);
            }
        }
    }




    private void setColorAtAllowedPosition()
    {
        Square allowed_square;


        // muestro los movimientos posibles
        for( int i = 0 ; i < possible_moves.size() ; i++ )
        {
            allowed_square = possible_moves.get(i).getTo();

            if(board.getPiece(allowed_square) == Piece.NONE)
            {
                if (my_color == Side.WHITE)
                {
                    DisplayBoardBackground[allowed_square.ordinal()/8][allowed_square.ordinal()%8].setBackgroundResource(R.color.colorPositionAvailable);}
                else
                {
                    DisplayBoardBackground[7-allowed_square.ordinal()/8][7-allowed_square.ordinal()%8].setBackgroundResource(R.color.colorPositionAvailable);
                }
            }
            else
            {
                if (my_color == Side.WHITE)
                {
                    DisplayBoardBackground[allowed_square.ordinal()/8][allowed_square.ordinal()%8].setBackgroundResource(R.color.colorDanger);
                }
                else
                {
                    DisplayBoardBackground[7-allowed_square.ordinal()/8][7-allowed_square.ordinal()%8].setBackgroundResource(R.color.colorDanger);
                }
            }
        }
    }




    private void resetColor()
    {
        for( int i = 0 ; i < SQUARES_QUANTITY ; i++ )
        {
            if ( (i / 8) % 2 == 0 )
            {
                if( i % 2 == 0)     DisplayBoardBackground[i / 8][i % 8].setBackgroundResource(R.color.colorBoardLight);
                else                DisplayBoardBackground[i / 8][i % 8].setBackgroundResource(R.color.colorBoardDark);
            }
            else
            {
                if (i % 2 != 0)     DisplayBoardBackground[i / 8][i % 8].setBackgroundResource(R.color.colorBoardLight);
                else                DisplayBoardBackground[i / 8][i % 8].setBackgroundResource(R.color.colorBoardDark);
            }
        }
    }




    private boolean isPawnFinish()
    {
        // analizo si el peón llego al final, para elegir la nueva pieza

        // si la pieza movida no fue un peón, me voy
        if ((board.getPiece(square_from) != Piece.BLACK_PAWN) && (board.getPiece(square_from) != Piece.WHITE_PAWN))     return false;


        if (my_color == Side.WHITE)
        {
            for ( int i = SQUARES_QUANTITY - 8 ; i < SQUARES_QUANTITY ; i++ )
            {
                if (square_to == Square.squareAt(i))
                {
                    LinearLayout_pawn_chioces.setVisibility(View.VISIBLE);

                    ImageView_pawn_queen.setImageDrawable(getDrawable(R.drawable.wqueen));
                    ImageView_pawn_rook.setImageDrawable(getDrawable(R.drawable.wrook));
                    ImageView_pawn_bishop.setImageDrawable(getDrawable(R.drawable.wbishop));
                    ImageView_pawn_knight.setImageDrawable(getDrawable(R.drawable.wknight));

                    isPawnSelectingPiece = true;

                    return true;
                }
            }
        }
        else
        {
            for ( int i = 0 ; i < 8 ; i++ )
            {
                if (square_to == Square.squareAt(i))
                {
                    LinearLayout_pawn_chioces.setVisibility(View.VISIBLE);

                    ImageView_pawn_queen.setImageDrawable(getDrawable(R.drawable.bqueen));
                    ImageView_pawn_rook.setImageDrawable(getDrawable(R.drawable.brook));
                    ImageView_pawn_bishop.setImageDrawable(getDrawable(R.drawable.bbishop));
                    ImageView_pawn_knight.setImageDrawable(getDrawable(R.drawable.bknight));

                    isPawnSelectingPiece = true;

                    return true;
                }
            }
        }

        return false;
    }




    public void pawnChoice(View v)
    {
        // cambio al peón que llego al final por la pieza elegida

        switch (v.getId())
        {
            case R.id.ImageView_pawn_queen :
                if (my_color == Side.WHITE)     board.doMove(new Move(square_from,square_to,Piece.WHITE_QUEEN));
                else                            board.doMove(new Move(square_from,square_to,Piece.BLACK_QUEEN));
                break;

            case R.id.ImageView_pawn_rook :
                if (my_color == Side.WHITE)     board.doMove(new Move(square_from,square_to,Piece.WHITE_ROOK));
                else                            board.doMove(new Move(square_from,square_to,Piece.BLACK_ROOK));
                break;

            case R.id.ImageView_pawn_bishop :
                if (my_color == Side.WHITE)     board.doMove(new Move(square_from,square_to,Piece.WHITE_BISHOP));
                else                            board.doMove(new Move(square_from,square_to,Piece.BLACK_BISHOP));
                break;

            case R.id.ImageView_pawn_knight :
                if (my_color == Side.WHITE)     board.doMove(new Move(square_from,square_to,Piece.WHITE_KNIGHT));
                else                            board.doMove(new Move(square_from,square_to,Piece.BLACK_KNIGHT));
                break;
        }

        setBoard();
        LinearLayout_pawn_chioces.setVisibility(View.INVISIBLE);
        isPawnSelectingPiece = false;
    }
}