/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen_ii_parcial_ii;

/**
 *
 * @author Mario
 */
public class Entry {

    public String username;
    public long pos;
    public long userId;
    public Entry next;

    public Entry(String username, long pos, long userId) {
        this.username = username;
        this.pos = pos;
        this.userId = userId;
        this.next = null;
    }
}
