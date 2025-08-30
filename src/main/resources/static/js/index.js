const mySlider = document.getElementById('mySlider');
            const sliderValueDisplay = document.getElementById('sliderValue');

            // Update the displayed value when the slider is moved
            mySlider.addEventListener('input', () => {
                sliderValueDisplay.textContent = mySlider.value + ' x ' + mySlider.value;
            });

            // Optionally, set the initial displayed value on page load
            sliderValueDisplay.textContent = mySlider.value + ' x ' + mySlider.value;