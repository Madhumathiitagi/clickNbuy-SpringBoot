
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

public class TestCloudinary {
    public static void main(String[] args) {
        try {
            String CLOUDINARY_URL = "your-cloudinary-url";
            Cloudinary cloudinary = new Cloudinary(CLOUDINARY_URL);
            System.out.println("Cloudinary initialized");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
