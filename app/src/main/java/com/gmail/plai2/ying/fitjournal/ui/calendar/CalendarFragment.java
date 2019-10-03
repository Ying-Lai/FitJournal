package com.gmail.plai2.ying.fitjournal.ui.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import com.gmail.plai2.ying.fitjournal.MainActivity;
import com.gmail.plai2.ying.fitjournal.R;
import com.gmail.plai2.ying.fitjournal.room.CompletedExerciseItem;
import com.gmail.plai2.ying.fitjournal.room.TypeConverters;
import com.gmail.plai2.ying.fitjournal.ui.workout.WorkoutViewModel;
import com.kizitonwose.calendarview.CalendarView;
import com.kizitonwose.calendarview.model.CalendarDay;
import com.kizitonwose.calendarview.model.CalendarMonth;
import com.kizitonwose.calendarview.model.DayOwner;
import com.kizitonwose.calendarview.ui.DayBinder;
import com.kizitonwose.calendarview.ui.ViewContainer;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;
import org.threeten.bp.YearMonth;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CalendarFragment extends Fragment {

    // Input fields
    private Set<LocalDate> mWorkoutDates = new HashSet<>();

    // UI fields
    private CalendarView mCalendarView;
    private DateTimeFormatter mMonthFormatter;
    private TextView mCalendarMonthTV;
    private TextView mCalendarYearTV;

    // Empty constructor
    public CalendarFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        // Initialize fields and variables
        View root = inflater.inflate(R.layout.fragment_calendar, container,false);
        mCalendarView = root.findViewById(R.id.calendar_view);
        mCalendarMonthTV = root.findViewById(R.id.calendar_month_tv);
        mCalendarYearTV = root.findViewById(R.id.calendar_year_tv);
        mMonthFormatter = DateTimeFormatter.ofPattern("MMMM");

        // Set boundaries for calendar
        YearMonth currentMonth = YearMonth.now();
        YearMonth firstMonth = currentMonth.minusMonths(36);
        YearMonth lastMonth = currentMonth.plusMonths(12);
        mCalendarView.setup(firstMonth, lastMonth, DayOfWeek.SUNDAY);
        mCalendarView.scrollToMonth(currentMonth);
        mCalendarView.setMaxRowCount(6);

        class DayViewContainer extends ViewContainer {

            // Fields
            private CalendarDay mCalendarDay;
            private TextView mCalendarDayTV;
            private View mCalendarDotV;
            private FrameLayout mCalendarDayBackgroundFL;

            private DayViewContainer(@NonNull View view) {
                super(view);
                mCalendarDayBackgroundFL = view.findViewById(R.id.calendar_day_fl);
                mCalendarDayTV = view.findViewById(R.id.calendar_day_tv);
                mCalendarDotV = view.findViewById(R.id.calendar_dot_v);

                // On click listener
                mCalendarDayTV.setOnClickListener((View dayView) -> {
                    Bundle bundle = new Bundle();
                    String dateInfo = TypeConverters.dateToString(mCalendarDay.getDate());
                    bundle.putString(MainActivity.DATE_INFO, dateInfo);
                    NavDestination currentDestination = Navigation.findNavController(dayView).getCurrentDestination();
                    if (currentDestination != null && currentDestination.getId() == R.id.navigation_to_calendar) {
                        if (mCalendarDay.getDate().equals(LocalDate.now())) {
                            Navigation.findNavController(dayView).navigate(R.id.to_workout_today);
                        } else {
                            Navigation.findNavController(dayView).navigate(R.id.to_workout_another_day, bundle);
                        }
                    }
                });
            }
        }

        // Day binder
        mCalendarView.setDayBinder(new DayBinder<DayViewContainer>() {
            @NonNull
            @Override
            public DayViewContainer create(@NonNull View view) {
                return new DayViewContainer(view);
            }

            @Override
            public void bind(@NonNull DayViewContainer viewContainer, @NonNull CalendarDay calendarDay) {
                viewContainer.mCalendarDay = calendarDay;
                String day = calendarDay.getDate().getDayOfMonth() + "";
                viewContainer.mCalendarDayTV.setText(day);
                if (calendarDay.getOwner() == DayOwner.THIS_MONTH) {
                    if (calendarDay.getDate().equals(LocalDate.now())) {
                        viewContainer.mCalendarDayBackgroundFL.setBackground(getResources().getDrawable(R.drawable.today_grid_background, null));
                    } else {
                        viewContainer.mCalendarDayBackgroundFL.setBackground(getResources().getDrawable(R.drawable.white_grid_background, null));
                    }
                } else {
                    if (calendarDay.getDate().equals(LocalDate.now())) {
                        viewContainer.mCalendarDayBackgroundFL.setBackground(getResources().getDrawable(R.drawable.today_grid_background2, null));
                    } else {
                        viewContainer.mCalendarDayBackgroundFL.setBackground(getResources().getDrawable(R.drawable.gray_grid_background, null));
                        viewContainer.mCalendarDayTV.setTextColor(getResources().getColor(R.color.gray, null));
                    }
                }
                if (mWorkoutDates.contains(calendarDay.getDate())) {
                    viewContainer.mCalendarDotV.setVisibility(View.VISIBLE);
                } else {
                    viewContainer.mCalendarDotV.setVisibility(View.GONE);
                }
            }
        });

        // Month scroll listener
        mCalendarView.setMonthScrollListener((CalendarMonth calendarMonth) -> {
            String year = calendarMonth.getYearMonth().getYear() + "";
            mCalendarYearTV.setText(year);
            mCalendarMonthTV.setText(mMonthFormatter.format(calendarMonth.getYearMonth()));
            return null;
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Observe live data
        WorkoutViewModel mViewModel = ViewModelProviders.of(this).get(WorkoutViewModel.class);
        mViewModel.getAllCompletedExercises().observe(getViewLifecycleOwner(), (List<CompletedExerciseItem> completedExerciseItems) -> {
            for (CompletedExerciseItem completedExerciseItem : completedExerciseItems) {
                mWorkoutDates.add(completedExerciseItem.getMExerciseDate());
            }
            mCalendarView.notifyCalendarChanged();
        });
    }

    // Other methods
}