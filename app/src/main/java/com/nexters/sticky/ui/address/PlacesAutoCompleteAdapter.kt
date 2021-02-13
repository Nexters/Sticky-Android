package com.nexters.sticky.ui.address

import android.content.Context
import android.graphics.Typeface
import android.text.style.CharacterStyle
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.TimeoutError
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Tasks
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.nexters.sticky.R


class PlacesAutoCompleteAdapter(setAddressActivity: SetAddressActivity) : RecyclerView.Adapter<PlacesAutoCompleteAdapter.PredictionHolder>(), Filterable {
	lateinit var context: Context
	val STYLE_BOLD: CharacterStyle = StyleSpan(Typeface.BOLD)
	val STYLE_NORMAL: CharacterStyle = StyleSpan(Typeface.NORMAL)
	val placesClient: PlacesClient = com.google.android.libraries.places.api.Places.createClient(setAddressActivity)
	private lateinit var clickListener: ClickListener
	var mResultList: ArrayList<PlaceAutocomplete> = ArrayList()
	lateinit var place: Place
	fun setClickListener(clickListener: ClickListener) {
		this.clickListener = clickListener
	}

	interface ClickListener {
		fun click(place: Place)
	}

	override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): PredictionHolder {
		val convertView: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.place_recycler_item_layout, viewGroup, false)
		return PredictionHolder(convertView)
	}

	override fun onBindViewHolder(predictionHolder: PredictionHolder, i: Int) {
		val item: PlaceAutocomplete = mResultList[i]
		val placeId = item.placeId.toString()
		val placeFields: List<Place.Field> = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS)
		val request = FetchPlaceRequest.builder(placeId, placeFields).build()
		placesClient.fetchPlace(request).addOnSuccessListener { response ->
			place = response.place
			predictionHolder.address.text = place.address
			predictionHolder.area.text = place.name
		}.addOnFailureListener { exception ->
			if (exception is ApiException) {
				Toast.makeText(context, exception.message + "", Toast.LENGTH_SHORT).show()
			}
		}
	}

	override fun getItemCount(): Int {
		return mResultList.size
	}

	override fun getFilter(): Filter {
		return object : Filter() {
			override fun performFiltering(constraint: CharSequence?): FilterResults {
				val results = FilterResults()
				if (constraint != null) {
					mResultList = getPredictions(constraint)!!
					// The API successfully returned results.
					results.values = mResultList
					results.count = mResultList.size
				}
				return results
			}

			override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
				if (results != null && results.count > 0) {
					notifyDataSetChanged()
				} else {
					//notifyDataSetInvalidated() not find data
				}
			}
		}
	}

	private fun getPredictions(constraint: CharSequence): ArrayList<PlaceAutocomplete> {
		val resultList: ArrayList<PlaceAutocomplete> = ArrayList()
		val token = AutocompleteSessionToken.newInstance()

		val request = FindAutocompletePredictionsRequest.builder()
			.setSessionToken(token)
			.setQuery(constraint.toString())
			.build()
		val autocompletePredictions = placesClient.findAutocompletePredictions(request)

		try {
			Tasks.await(autocompletePredictions, 60, java.util.concurrent.TimeUnit.SECONDS)
		} catch (e: Exception) {
			e.printStackTrace()
		} catch (e: InterruptedException) {
			e.printStackTrace()
		} catch (e: TimeoutError) {
			e.printStackTrace()
		}
		return if (autocompletePredictions.isSuccessful) {
			val findAutocompletePredictionsResponse = autocompletePredictions.result
			if (findAutocompletePredictionsResponse != null) for (prediction in findAutocompletePredictionsResponse.autocompletePredictions) {
				resultList.add(PlaceAutocomplete(prediction.placeId, prediction.getPrimaryText(STYLE_NORMAL).toString(), prediction.getFullText(STYLE_BOLD).toString()))
			}
			resultList
		} else {
			resultList
		}
	}


	class PlaceAutocomplete(var placeId: CharSequence, var area: CharSequence, var address: CharSequence) {
		override fun toString(): String {
			return area.toString()
		}
	}

	inner class PredictionHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val address: TextView = itemView.findViewById(R.id.address)
		val area: TextView = itemView.findViewById(R.id.area)

		init {
			itemView.setOnClickListener {
				val item: PlaceAutocomplete = mResultList[adapterPosition]
				val placeId = item.placeId.toString()
				val placeFields: List<Place.Field> = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS)
				val request = FetchPlaceRequest.builder(placeId, placeFields).build()
				placesClient.fetchPlace(request).addOnSuccessListener { response ->
					place = response.place
					clickListener.click(place)
				}.addOnFailureListener { exception ->
					if (exception is ApiException) {
						Toast.makeText(context, exception.message + "", Toast.LENGTH_SHORT).show()
					}
				}
			}
		}
	}
}
