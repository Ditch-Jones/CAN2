package com.example.eridhobufferyrollian.beispielsql.model;

/**
 * Created by en on 15.06.17.
 * Class haupt daten
 */

public class Node {

    private static final int maxPeers= 3;


    private long   uid;
    private double cornerTopRightX;
    private double cornerTopRightY;
    private double cornerTopLeftX;
    private double cornerTopLeftY;
    private double cornerBottomRightX;
    private double cornerBottomRightY;
    private double cornerBottomLeftX;
    private double cornerBottomLeftY;
    private double punktX;
    private double punktY;
    private String IP;
    private int    countPeers;
    private boolean checked;
    Neighbour neighbour;


    /**
     *
     * @param uid
     * @param checked
     * @param cornerTopRightX
     * @param cornerTopRightY
     * @param cornerTopLeftX
     * @param cornerTopLeftY
     * @param cornerBottomRightX
     * @param cornerBottomRightY
     * @param cornerBottomLeftX
     * @param cornerBottomLeftY
     * @param punktX
     * @param punktY
     * @param IP
     * @param countPeers
     */
//    public Node(long uid, boolean checked,
//                     double cornerTopRightX, double cornerTopRightY, double cornerTopLeftX, double cornerTopLeftY,
//                     double cornerBottomRightX, double cornerBottomRightY, double cornerBottomLeftX, double cornerBottomLeftY,
//                     double punktX, double punktY, String IP, int countPeers) {
//
//        this.uid                 = uid;
//        this.checked             = checked;
//        this.cornerTopRightX     = cornerTopRightX;
//        this.cornerTopRightY     = cornerTopRightY;
//        this.cornerTopLeftX      = cornerTopLeftX;
//        this.cornerTopLeftY      = cornerTopLeftY;
//        this.cornerBottomRightX  = cornerBottomRightX;
//        this.cornerBottomRightY  = cornerBottomRightY;
//        this.cornerBottomLeftX   = cornerBottomLeftX;
//        this.cornerBottomLeftY   = cornerBottomLeftY;
//        this.punktX              = punktX;
//        this.punktY              = punktY;
//        this.IP                  = IP;
//        this.countPeers          = countPeers;
//    }

    /**
     * Diese Methode liefert einen x-Wert der zwischen 0 und 1 liegt
     * Es wird durch 2552552552lgeteilt, da so Werte zwischen 0 und 1 liegt
     * @param ip Anhand der IP wird ein x-Wert berechnet
     * @return Gebe einen double X-Wert zurück
     */
    public static double hashX(String ip) {
        double x = ip.hashCode();
        if(x < 0){
            x = x/(-2552552552l);
            return x;
        }else{
            x = x/2552552552l;
            return x;
        }
    }

    /**
     * Diese Methode liefert einen Y-Wert der zwischen 0 und 1 liegt
     * Es wird durch 2552552552l geteilt, da so Werte zwischen 0 und 1 liegen und die IP wird von hinten nach vorne gelesen durch Methode-Umkehren
     * @param ip Anhand der IP wird ein Y-Wert berechnet
     * @return Gebe einen double Y-Wert zurück
     */
    public static double hashY(String ip){
        String hash2 = umkehren(ip);
        double y = hash2.hashCode();
        if(y < 0){
            y = y/(-2552552552l);
            return y;
        }else{
            y = y/2552552552l;
            return y;
        }
    }

    /**
     * Methode zum umkehren von einer IP-Adresse
     * @param ip Eine IP-Adresse
     * @return Gibt die IP-Adresse umgekehrt zurück
     */
    public static String umkehren( String ip )
    {
        String umgekehrt = new String();

        for ( int j = ip.length()-1; j >= 0; j-- )
            umgekehrt += ip.charAt(j);

        return umgekehrt;
    }


    /**
     * Methode zum Testen ob ein Knoten/Bild in der eigenen Zone liegt
     * @param x
     * @param y
     * @return True falls Knoten/Bild in der Zone liegt, false falls Knoten/bild nicht in der Zone liegt
     */
    public boolean checkIfInMyZone(double x, double y) {
        if(x > cornerTopLeftX && x <= cornerTopRightX)
        {
            if (y > cornerBottomLeftY && y <= cornerTopLeftY ) {
                return true;
            }
        }
        return false;
    }

    /**
     * Methode die den Routing-Vorgang weiterleitet falls das Ziel noch nicht erreicht wurde
     * @param ip IP des zu routenden Knoten
     * @param x X-Wert des zu routenden Knoten
     * @param y Y-Wert des zu routenden Knoten
     * @param id kann jeweils FotoID oder UID sein, wird benötigt sodass man seinen Peers die nötigen Informationen zu dem neuen Knoten geben kann
     * @param isNode Dient zur unterscheidung von Knoten und Bildern
     */
    private void receiveRoutingRequest(String ip, double x, double y, int id, boolean isNode) {
        if(checkIfInMyZone(x,y)){
            if(isNode){
                // wie bekomme ich das jeweilige socketobject zum versenden
                //// TODO: 14.08.2017  Reply to Request-Method(muss setPeers(mit sich selbst) und setNeighbours mitsenden)
                //// TODO: 14.08.2017 Muss aktuelle Peers über den neuen Knoten Informieren, sodass diese ihre Peerliste updaten. Nun update deine eigene Peerlist
                if(checkIfMaxPeersCount()){
                    //// TODO: 15.08.2017 informiere deine Peers das sie nun Splitten müssen// methode die einen Splitt aufruft
                    //// TODO: 14.08.2017 SPLITT
                }
            }else {
                //// TODO: 15.08.2017 Verbindungsaufbau zu der ip um Bild herunterzuladen und dann zu speichern
                /// TODO: 15.08.2017 verbindungsaufbau zu Peers und diesen werden die Informationen zum Bild übermittelt und nun laden sie sich das Bild von zuletzt gerouteten Node herunter
            }
        }
        //fortsetzung des routing
        routing(ip,x,y,id,isNode);
    }

    /**
     * Routing Methode: In der Routing-Methode wird die Distanz zu allen Nachbarn berechnet und zu dem routet zu dem die Distanz am geringsten ist
     * @param ip Des zu routenden Knoten/Bild
     * @param x Des zu routenden Knoten/Bild
     * @param y Des zu routenden Knoten/Bild
     * @param id Des zu routenden Knoten/Bild
     * @param isNode Ist es ein Knoten? Wenn nicht dann ist es ein Bild
     */
    private void routing(String ip, double x ,double y, int id, boolean isNode){
        double neighbourX, neighbourY;
        double [] distances = new double[4];

        for(int i=0; i<=distances.length ; i++){
            // gibt getNeighbour ein Objekt wieder?
            if(getNeighbour(i) != null){
                neighbour = getNeighbour(i);

                neighbourX = neighbour.getPunktX();
                neighbourY = neighbour.getPunktY();

                distances[i] = computeDistance(x,y,neighbourX,neighbourY);
            }
        }
        int index = compareValues(distances);
        //// TODO: 14.08.2017 Verbindungsaufbau zu dem Neighbour der an Stelle == Index steht und IP und x,y-Werte übertragen so das dieser weiter routen kann
    }

    /**
     * Methode um ein Bild auf dem Gerät und dann auch im CAN zu löschen
     * @param id Des zu löschenden Bildes
     * @param x Des Bildes
     * @param y Des Bildes
     */
    private void delPicInCan(String ip, int id, double x, double y){
        // TODO: 15.08.2017 Erst muss delPicInCan aufgerufen werden bevor das Bild auf dem eigenen Gerät gelöscht wird
        // TODO: 15.08.2017 checke deinen foreignData Table um zu sehen ob die id,x und y übereinstimmen, Falls dies der Fall ist lösche das Bild
        // TODO: 15.08.2017
        // benötigen die Methoden getID, getX und getY auf den foreignDataTable, @somar wie löscht man ein Bild auf dem Gerät
    }

    /**
     * Diese Methode wird aufgerufen wenn ein neues Bild geschossen wurde und in CAN eingefügt werden soll
     * @param ip IP des Besitzers
     * @param x
     * @param y
     * @param fotoId
     */
    public void placePicInCan(String ip, double x, double y, int fotoId){
        receiveRoutingRequest(ip, x, y, fotoId, false);
    }
    /**
     * Diese Methode berechnet die Distanz zwischen den zu Routenden Knoten und den Neighbours des aktuellen Knotens(der routet)
     * @param x Des zu routenden Knoten
     * @param y Des zu routenden Knoten
     * @param neighbourX
     * @param neighbourY
     * @return Die Distanz zwischen den 2 Punkten
     */
    public double computeDistance(double x, double y, double neighbourX, double neighbourY) {
        double dis = Math.abs(x - neighbourX) + Math.abs(y - neighbourY);
        return dis;
    }

    /**
     * Methode mit der ein neuer Knoten seinen Peers seine ID und IP (zweck=zum Eintragen in PeersDB) mitteilen kann
     * @param ip
     */
    private void informPeersAboutYourself(String ip) {
        //// TODO: 14.08.2017    user.getUid(); von DB, user.getIP von DB
        //// TODO: 14.08.2017 sende an alle deine Peers ein setPeer mit diesen Informationen

    }
    /**
     * Vergleiche alle Distanzen der Nachbarn
     * @param distances Array mit allen Distanzen der Neighbour zu dem zu routenden Knoten
     * @return den index(in NeighbourDB) mit der geringsten Distanz
     */
    public int compareValues(double [] distances){
        int index = 0;
        double temp =  distances[0];
        for(int i= 1 ; i< distances.length; i++){
            if(temp > distances[i]){

                temp = distances[i];
                index = i;
            }
        }
        return index;
    }

    public void requestJoin_for_GUI()
    {
        requestJoin();
    }

    /**
     * Mit dieser Methode findet ein neuer Knoten einen Einstiegspunkt in das CAN, indem er den Bootstrapserver nach einer IP anfragt
     */
    private void requestJoin(){
        //// TODO: 15.08.2017 getBootsTrapIP() Methode
        //// TODO: 15.08.2017 nun Verbindung zu dieser IP herstellen und routing-Anfrage mit(eigener IP und x ,y Werten, id und isNode als Parameter)
    }

    /**
     * Methode die aufgerufen wird wenn das routing beendet ist und die DB's des neuen Knoten updaten muss
     * @param ip Des neuen Knotens
     */
    private void replyToRequest(String ip){
        //// TODO: 15.08.2017 Verbindung zu IP herstellen, und setPeer und setNeighbour aufrufen auf diesem Knoten(mit den eigenen Peers und Neighbour-Werten)
        //// TODO: 15.08.2017 nach update der eigenen PeersDB muss überprüft werden ob die Anzahl Peers nun 3 beträgt, falls dies der Fall ist => Split
    }

    public void increasePeersCount(){
        if(checkIfMaxPeersCount()){

        }else{
            countPeers++;
        }
    }
    public void decreasePeersCount(){
        if(countPeers < 1){

        }else{
            countPeers--;
        }
    }

    // TODO: 15.08.2017 Vielleicht so implementieren das hier auch noch gecheckt wird ob gesplittet wird
    private boolean checkIfMaxPeersCount(){
        if (countPeers == maxPeers){
            return true;
        }else{
            return false;
        }
    }



    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public double getCornerTopRightX() {
        return cornerTopRightX;
    }

    public void setCornerTopRightX(double cornerTopRightX) {
        this.cornerTopRightX = cornerTopRightX;
    }

    public double getCornerTopRightY() {
        return cornerTopRightY;
    }

    public void setCornerTopRightY(double cornerTopRightY) {
        this.cornerTopRightY = cornerTopRightY;
    }

    public double getCornerTopLeftX() {
        return cornerTopLeftX;
    }

    public void setCornerTopLeftX(double cornerTopLeftX) {
        this.cornerTopLeftX = cornerTopLeftX;
    }

    public double getCornerTopLeftY() {
        return cornerTopLeftY;
    }

    public void setCornerTopLeftY(double cornerTopLeftY) {
        this.cornerTopLeftY = cornerTopLeftY;
    }

    public double getCornerBottomRightX() {
        return cornerBottomRightX;
    }

    public void setCornerBottomRightX(double cornerBottomRightX) {
        this.cornerBottomRightX = cornerBottomRightX;
    }

    public double getCornerBottomRightY() {
        return cornerBottomRightY;
    }

    public void setCornerBottomRightY(double cornerBottomRightY) {
        this.cornerBottomRightY = cornerBottomRightY;
    }

    public double getCornerBottomLeftX() {
        return cornerBottomLeftX;
    }

    public void setCornerBottomLeftX(double cornerBottomLeftX) {
        this.cornerBottomLeftX = cornerBottomLeftX;
    }

    public double getCornerBottomLeftY() {
        return cornerBottomLeftY;
    }

    public void setCornerBottomLeftY(double cornerBottomLeftY) {
        this.cornerBottomLeftY = cornerBottomLeftY;
    }


    public boolean isChecked() {
        return checked;
    }

    public void setChecked (boolean checked) {
        this.checked = checked;
    }

//    public double getCornerTopRight() {
//        return cornerTopRight;
//    }
//
//    public void setCornerTopRight(double cornerTopRight) {
//        this.cornerTopRight = cornerTopRight;
//    }
//
//    public double getCornerTopLeft() {
//        return cornerTopLeft;
//    }
//
//    public void setCornerTopLeft(double cornerTopLeft) {
//        this.cornerTopLeft = cornerTopLeft;
//    }
//
//    public double getCornerBottomRight() {
//        return cornerBottomRight;
//    }
//
//    public void setCornerBottomRight(double cornerBottomRight) {
//        this.cornerBottomRight = cornerBottomRight;
//    }
//
//    public double getCornerBottomLeft() {
//        return cornerBottomLeft;
//    }
//
//    public void setCornerBottomLeft(double cornerBottomLeft) {
//        this.cornerBottomLeft = cornerBottomLeft;
//    }

    public double getPunktX() {
        return punktX;
    }

    public void setPunktX(double punktX) {
        this.punktX = punktX;
    }

    public double getPunktY() {
        return punktY;
    }

    public void setPunktY(double punktY) {
        this.punktY = punktY;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public int getCountPeers() {
        return countPeers;
    }
    public void setCountPeers(int countPeers) {
        this.countPeers = countPeers;
    }

    @Override
    public String toString() {
        String output = uid + " -- " + IP +
                "\nCorner top Left : x -> "+ cornerTopLeftX + " -- y -> "+ cornerTopLeftY +
                "\nCorner top Right : x -> "+ cornerTopRightX + " -- y -> "+ cornerTopRightY +
                "\nCorner Bottom Left : x -> "+ cornerBottomLeftX + " -- y -> "+ cornerBottomLeftY +
                "\nCorner Bottom Right : x -> "+ cornerBottomRightX + " -- y -> "+ cornerBottomRightY +
                "\nCorner Punkt : x -> "+ punktX + " -- y -> "+ punktY +
                "\nCorner CountPeers : "+ countPeers;
        return output;
    }
}