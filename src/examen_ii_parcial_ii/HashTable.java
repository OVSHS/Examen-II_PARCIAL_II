/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen_ii_parcial_ii;

/**
 *
 * @author Mario
 */
public class HashTable {
  private Entry head;

    public void add(String username, long pos, long userId) {
        Entry nuevo = new Entry(username, pos, userId);
        if (head == null) {
            head = nuevo;
        } else {
            Entry temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = nuevo;
        }
    }

    public void remove(String username) {
        if (head == null) {
            return;
        }
        if (head.username.equals(username)) {
            head = head.next;
            return;
        }
        Entry temp = head;
        while (temp.next != null) {
            if (temp.next.username.equals(username)) {
                temp.next = temp.next.next;
                return;
            }
            temp = temp.next;
        }
    }

    public long search(String username) {
        Entry temp = head;
        while (temp != null) {
            if (temp.username.equals(username)) {
                return temp.pos;
            }
            temp = temp.next;
        }
        return -1;
    }

    public Entry findEntry(String username) {
        Entry temp = head;
        while (temp != null) {
            if (temp.username.equals(username)) {
                return temp;
            }
            temp = temp.next;
        }
        return null;
    
    }
}
