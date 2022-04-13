package com.example.android.politicalpreparedness.presentation.representative

import androidx.lifecycle.MutableLiveData
import com.example.android.politicalpreparedness.data.network.models.Address
import com.example.android.politicalpreparedness.domain.GetRepresentativeUseCase
import com.example.android.politicalpreparedness.domain.base.BaseViewModel
import com.example.android.politicalpreparedness.domain.base.Resource
import com.example.android.politicalpreparedness.presentation.representative.model.Representative

class RepresentativeViewModel(
    private val getRepresentativeUseCase: GetRepresentativeUseCase
): BaseViewModel() {

    // Establish live data for representatives and address
    val addressLiveData = MutableLiveData<Resource<Address>>()

     val representativeLiveData = MutableLiveData<Resource<List<Representative>>>()
    // Create function to fetch representatives from API from a provided address

    /**
     *  The following code will prove helpful in constructing a representative from the API. This code combines the two nodes of the RepresentativeResponse into a single official :

    val (offices, officials) = getRepresentativesDeferred.await()
    _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }

    Note: getRepresentatives in the above code represents the method used to fetch data from the API
    Note: _representatives in the above code represents the established mutable live data housing representatives

     */
    //the logic is demanding to repository
   private fun getRepresentatives() {
        // todo
        //  if (validateEnteredData()) {
            //val address = getAddress()
        val address = Address( // MOCK LOCATION, IN ITALY, TUSCANY, RESPONSE IS 404
             line1="Amphithatre Parkway",
         line2= "1600",
         city= "Mountain View",
         state= "California",
         zip = "94043"
        )
            getRepresentativeUseCase.executeAndDispose(representativeLiveData, address)
//        }

    }

    // Create function get address from geo location
    fun setLocation(newAddress: Address) {
        addressLiveData.value = Resource.success(Address(
            line1 = newAddress.line1,
            line2 = newAddress.line2,
            city = newAddress.city,
            state = newAddress.state,
            zip = newAddress.zip)
        )
        getRepresentatives()
    }

    // Create function to get address from individual fields
    private fun getAddress(): Address = addressLiveData.value!!.data!!

}
