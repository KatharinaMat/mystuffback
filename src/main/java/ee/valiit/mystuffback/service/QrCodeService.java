package ee.valiit.mystuffback.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QrCodeService {

    @Value("${mystuff.server.address}")
    private String serverAddress;

    @Value("${mystuff.item.path}")
    private String itemPath;

    public String getQrCode(Integer itemId) {
        return constructImageQrLink(itemId);
    }

    private String constructImageQrLink(Integer itemId) {
        return serverAddress + itemPath + itemId;
    }
}
