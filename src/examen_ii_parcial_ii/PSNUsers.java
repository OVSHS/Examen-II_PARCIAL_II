/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen_ii_parcial_ii;

/**
 *
 * @author Mario
 */
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.io.IOException;

public class PSNUsers {

 private RandomAccessFile psn;
    public HashTable users;

    public PSNUsers() {
        users = new HashTable();
        try {
            psn = new RandomAccessFile("psn.dat", "rw");
            reloadHashTable();
        } catch (IOException e) {
        }
    }

    private void reloadHashTable() {
        try {
            psn.seek(0);
            while (psn.getFilePointer() < psn.length()) {
                byte tipo = psn.readByte();
                if (tipo == 0) {
                    long posUser = psn.getFilePointer() - 1;
                    long uid = psn.readLong();
                    String nombreUsuario = psn.readUTF();
                    int puntos = psn.readInt();
                    int cantidadTrofeos = psn.readInt();
                    String estado = psn.readUTF();
                    users.add(nombreUsuario, posUser, uid);
                } else if (tipo == 1) {
                    long skipUid = psn.readLong();
                    psn.readUTF();
                    psn.readUTF();
                    psn.readUTF();
                    psn.readUTF();
                    psn.readUTF();
                } else {
                    break;
                }
            }
        } catch (IOException e) {
        }
    }

    public void addUser(String username) {
        if (username == null || username.trim().isEmpty()) {
            return;
        }
        if (users.size() >= 1) {
            return;
        }
        if (users.search(username) != -1) {
            return;
        }
        try {
            long newId = System.currentTimeMillis();
            psn.seek(psn.length());
            long posUser = psn.getFilePointer();
            psn.writeByte(0);
            psn.writeLong(newId);
            psn.writeUTF(username);
            psn.writeInt(0);
            psn.writeInt(0);
            psn.writeUTF("Si");
            users.add(username, posUser, newId);
        } catch (IOException e) {
        }
    }

    public void deactivateUser(String username) {
        if (username == null || username.trim().isEmpty()) {
            return;
        }
        long pos = users.search(username);
        if (pos == -1) {
            return;
        }
        try {
            psn.seek(pos);
            psn.readByte();
            psn.readLong();
            psn.readUTF();
            psn.readInt();
            psn.readInt();
            psn.writeUTF("No");
        } catch (IOException e) {
        }
    }

    public void addTrophieTo(String username, String game, String trophyName, Trophy type) {
        if (username == null || username.trim().isEmpty()) {
            return;
        }
        if (game == null) {
            game = "";
        }
        if (trophyName == null) {
            trophyName = "";
        }
        long pos = users.search(username);
        if (pos == -1) {
            return;
        }
        try {
            psn.seek(pos);
            psn.readByte();
            long uid = psn.readLong();
            String nombreUsuario = psn.readUTF();
            int puntos = psn.readInt();
            int trofeos = psn.readInt();
            String estado = psn.readUTF();
            if (!estado.equals("Si")) {
                return;
            }
            int nuevosPuntos = puntos + type.points;
            int nuevosTrofeos = trofeos + 1;
            psn.seek(pos + 1 + 8 + calcularUTFBytes(nombreUsuario));
            psn.writeInt(nuevosPuntos);
            psn.writeInt(nuevosTrofeos);
            psn.writeUTF("Si");
            psn.seek(psn.length());
            psn.writeByte(1);
            psn.writeLong(uid);
            psn.writeUTF(nombreUsuario);
            psn.writeUTF(type.name());
            psn.writeUTF(game);
            psn.writeUTF(trophyName);
            String fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            psn.writeUTF(fecha);
        } catch (IOException e) {
        }
    }

    public String playerInfo(String username) {
        if (username == null || username.trim().isEmpty()) {
            return "Usuario no encontrado";
        }
        long pos = users.search(username);
        if (pos == -1) {
            return "Usuario no encontrado";
        }
        StringBuilder sb = new StringBuilder();
        try {
            psn.seek(pos);
            psn.readByte();
            long uid = psn.readLong();
            String nombreUsuarioLeido = psn.readUTF();
            int puntos = psn.readInt();
            int trofeos = psn.readInt();
            String estado = psn.readUTF();
            sb.append("Usuario: ").append(nombreUsuarioLeido).append("\n");
            sb.append("Puntos: ").append(puntos).append("\n");
            sb.append("Trofeos: ").append(trofeos).append("\n");
            sb.append("Activo: ").append(estado).append("\n");
            psn.seek(0);
            while (psn.getFilePointer() < psn.length()) {
                byte regTipo = psn.readByte();
                if (regTipo == 1) {
                    long trophyUid = psn.readLong();
                    String nombreUsuarioTrofeo = psn.readUTF();
                    String tipoTrofeo = psn.readUTF();
                    String nombreJuego = psn.readUTF();
                    String nombreDelTrofeo = psn.readUTF();
                    String fecha = psn.readUTF();
                    if (trophyUid == uid) {
                        sb.append(fecha).append(" - ")
                          .append(tipoTrofeo).append(" - ")
                          .append(nombreJuego).append(" - ")
                          .append(nombreDelTrofeo).append("\n");
                    }
                } else if (regTipo == 0) {
                    psn.readLong();
                    psn.readUTF();
                    psn.readInt();
                    psn.readInt();
                    psn.readUTF();
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            return "Error al leer informacion";
        }
        return sb.toString();
    }

    private int calcularUTFBytes(String s) {
        try {
            java.io.ByteArrayOutputStream flujoMemoria = new java.io.ByteArrayOutputStream();
            java.io.DataOutputStream escritorUTF = new java.io.DataOutputStream(flujoMemoria);
            escritorUTF.writeUTF(s);
            escritorUTF.close();
            return flujoMemoria.toByteArray().length;
        } catch (IOException e) {
            return 0;
        }
    }
}
