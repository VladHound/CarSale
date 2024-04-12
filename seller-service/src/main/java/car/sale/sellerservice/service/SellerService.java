package car.sale.sellerservice.service;

import car.sale.auth.dto.ClientAuthDetails;
import car.sale.sellerservice.dto.SellerRegistration;
import car.sale.sellerservice.dto.SellerRequestDto;
import car.sale.sellerservice.dto.SellerResponseDto;
import car.sale.sellerservice.dto.UserToSellerResponse;
import car.sale.sellerservice.mapper.SellerMapper;
import car.sale.sellerservice.model.Seller;
import car.sale.sellerservice.repository.SellerRepository;
import com.nimbusds.jose.shaded.gson.Gson;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SellerService {
    private final SellerRepository sellerRepository;

    private final SellerMapper sellerMapper;


    private final Gson gson = new Gson();

    @Value("${spring.kafka.topic}")
    private String kafkaTopic;

    @Value("${spring.kafka.consumer.group-id}")
    private String kafkaGroupId;

    private static final String SELLER_NOT_FOUND_EXCEPTION = "Seller not found with seller id = ";

/*
    @Transactional
    public UUID createSeller(SellerRegistration sellerRegistration) {
        if (sellerRepository.existsSellerByEmail(sellerRegistration.email())) {
            throw new EntityExistsException("Seller with email " + sellerRegistration.email() + " already exist");
        }

        Seller seller = sellerMapper.toSeller(sellerRegistration);
        return sellerRepository.save(seller).getExternalId();
    }
*/

    //    @KafkaListener(topics = "${spring.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    @KafkaListener(topics = "user-seller-topic", groupId = "seller-service")
    public UUID createSellerAutoWithKafka(String userJson) {
        System.out.println("Listened user: " + userJson);

        UserToSellerResponse userRequest = gson.fromJson(userJson, UserToSellerResponse.class);

        Seller seller = sellerMapper.toSeller(new SellerRegistration(userRequest.email(), userRequest.password()));

        seller.setFirstName(userRequest.firstName());
        seller.setLastName(userRequest.lastName());

        return sellerRepository.save(seller).getExternalId();
    }

    public SellerResponseDto getSellerBySellerId(UUID sellerId) {
        return sellerMapper.toSellerResponseDto(sellerRepository
                .findByExternalId(sellerId)
                .orElseThrow(() -> new EntityNotFoundException(SELLER_NOT_FOUND_EXCEPTION + sellerId)));
    }

    @Transactional
    public ClientAuthDetails getSellerAuthDetails(String email) {
        return sellerRepository.findSellerAuthDetailsByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("No seller with email " + email));
    }

    @Transactional
    @Deprecated
    public SellerResponseDto updateSeller(UUID sellerId, SellerRequestDto sellerRequestDto) {
        Seller seller = sellerRepository
                .findByExternalId(sellerId)
                .orElseThrow(() -> new EntityNotFoundException(SELLER_NOT_FOUND_EXCEPTION + sellerId));
        sellerMapper.updateSellerModel(sellerRequestDto, seller);
        return sellerMapper.toSellerResponseDto(seller);
    }

    @Transactional
    public void deleteSellerBySellerId(UUID sellerId) {
        Seller seller = sellerRepository
                .findByExternalId(sellerId)
                .orElseThrow(() -> new EntityNotFoundException(SELLER_NOT_FOUND_EXCEPTION + sellerId));
        sellerRepository.deleteById(seller.getId());
    }
}