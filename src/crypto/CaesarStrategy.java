package crypto;

public class CaesarStrategy implements EncryptionStrategy{

  public String encrypt(String data, String key) {
    int shift = Integer.parseInt(key);
    String result = "";
    char c;
    for(int i = 0; i < data.length(); i++){
      c = data.charAt(i);
      result = result + (char)(c + shift);
    }
    return result;
  }

  public String decrypt(String data, String key) {
    int shift = Integer.parseInt(key);
    String result = "";
    char c;
    for(int i = 0; i < data.length(); i++){
      c = data.charAt(i);
      result = result + (char)(c - shift);
    }
    return result;
  }
  
}
