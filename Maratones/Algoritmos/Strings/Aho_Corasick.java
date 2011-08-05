import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Aho_Corasick {
  static class PMA {
    PMA next[];
    PMA fail;
    List<Integer> accept;
    PMA() {
      next = new PMA[256]; // Se puede cambiar por un Mapa
      accept = new ArrayList<Integer>(); // Se puede cambiar por una linked list especial
    }                                    // en la cual addAll sea constante
  }

  static PMA buildPMA(char p[][], int size) {
    PMA root = new PMA();
    for (int i = 0; i < size; i++) {
      PMA t = root;
      for (int j = 0; j < p[i].length; ++j) {
        char c = p[i][j];
        if (t.next[c] == null)
          t.next[c] = new PMA();
        t = t.next[c];
      }
      t.accept.add(i);
    }
    for(int c = 'a' ; c <= 'z' ; c++)
      if(root.next[c] == null)
        root.next[c] = root;
    Queue<PMA> Q = new LinkedList<PMA>();
    for (int c = 'a'; c <= 'z'; c++) {
      if (root.next[c] != root) {
        root.next[c].fail = root;
        Q.add(root.next[c]);
      }
    }
    while (!Q.isEmpty()) {
      PMA t = Q.poll();
      for (int c = 'a'; c <= 'z'; c++) {
        if (t.next[c] != null) {
          Q.add(t.next[c]);
          PMA r = t.fail;
          while (r.next[c] == null)
            r = r.fail;
          t.next[c].fail = r.next[c];
          t.next[c].accept.addAll(r.next[c].accept); // ojo!! con esta linea, a matchea 10 veces en aaaaaaaaaa
        }
      }
    }
    return root;
  }

  static void match(char t[], PMA v, int result[]) {
    int n = t.length;
    for (int i = 0; i < n; i++) {
      char c = t[i];
      while (v.next[c] == null)
        v = v.fail;
      v = v.next[c];
      for (int j : v.accept){
        result[j] = 1;
      }
    }
  }

  static int kmp(char t[], char p[], int fail[]) {
    int n = t.length;
    int m = p.length;
    for (int i = 0, k = 0; i < n; i++) {
      while (k >= 0 && p[k] != t[i])
        k = fail[k];
      if (++k >= m) {
        return 1;
      }
    }
    return 0;
  }

  static int[] buildFail(char p[]) {
    int m = p.length;
    int fail[] = new int[m + 1];
    int j = fail[0] = -1;
    for (int i = 1; i <= m; i++) {
      while (j >= 0 && p[j] != p[i - 1])
        j = fail[j];
      fail[i] = ++j;
    }
    return fail;
  }

  void getRay(char grid[][], int x, int y, char ray[]) {
    int cx = 0;
    int cy = 0;
    for (int i = 0; i < ray.length; i++) {
      ray[i] = grid[cy][cx];
      cx += x;
      cy += y;
      if (cy >= grid.length)
        cy -= grid.length;
      if (cx >= grid[0].length)
        cx -= grid[0].length;
    }
  }

  static int gcd(int a, int b) {
    if (b == 0)
      return a;
    return gcd(b, a % b);
  }

  public int[] countRays(String[] g, String[] words, int k) {
    int H = g.length;
    int W = g[0].length();
    int wnum = words.length;
    char grid[][] = new char[H][W];
    for (int i = 0; i < grid.length; i++)
      grid[i] = g[i].toCharArray();
    boolean check[][] = new boolean[H][W];
    int memo[][][] = new int[H][W][wnum];
    int res[] = new int[wnum];
    char ray[] = new char[H * W + 50];
    char ws[][] = new char[wnum][];
    for (int i = 0; i < ws.length; i++)
      ws[i] = words[i].toCharArray();    
    PMA pma = buildPMA(ws, wnum);
    for (int x = 0; x <= k; x++) {
      for (int y = 0; y <= k; y++) {
        int gcd = gcd(x, y);
        if (gcd != 1) continue;
        int a = x % W;
        int b = y % H;
        if (!check[b][a]) {
          getRay(grid, a, b, ray);
          match(ray, pma, memo[b][a]);
          check[b][a] = true;
        }
        for (int i = 0; i < wnum; i++)
          res[i] += memo[b][a][i];
      }
    }
    return res;
  }
}
